package com.example.simplearapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.MotionEvent;

import com.AppData;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.AnimationData;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.PlaneRenderer;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.concurrent.CompletableFuture;

import static android.graphics.Color.YELLOW;

public class ArObjectHelper {
    private static final String TAG = ArObjectHelper.class.getSimpleName();
    private Context context;

    private ArFragment arFragment;
    //AR Object
    private ModelRenderable modelRenderable;

    private OBJECT_TYPE object_type;
    private boolean isAnimateObject = true;
    private boolean isAnimationObjectRunning = false;

    private boolean isDeleteMode = false;


    public void setAnimateObject(boolean animateObject) {
        isAnimateObject = animateObject;
    }

    // AR Object Type resource 를 제외한 나머지 3개는 라이브러리에서 제공된다.
    public enum OBJECT_TYPE {
        CUBE,
        CYLINDER,
        SPHERE,
        RESOURCE
    }

    public void setDeleteMode(boolean deleteMode) {
        isDeleteMode = deleteMode;
    }

    public void setObject_type(OBJECT_TYPE object_type) {
        this.object_type = object_type;
    }

    public ArObjectHelper(Context context, ArFragment arFragment) {
        this.context = context;
        this.arFragment = arFragment;
    }

    public void setModel(OBJECT_TYPE object_type) {
        this.object_type = object_type;
        setAnimObjectProperty();

        buildObject(object_type);
    }

    private void buildObject(OBJECT_TYPE object_type) {
        switch (object_type) {
            case CUBE:
                MaterialFactory.makeOpaqueWithColor(context, new Color(YELLOW))
                        .thenAccept(material -> {
                            Vector3 vector3 = new Vector3(0.05f, 0.05f, 0.05f);
                            modelRenderable = ShapeFactory.makeCube(vector3, Vector3.zero(), material);
                            modelRenderable.setShadowCaster(false);
                            modelRenderable.setShadowReceiver(false);
                        });
                break;
            case CYLINDER:
                MaterialFactory.makeOpaqueWithColor(context, new Color(YELLOW))
                        .thenAccept(material -> {
                            modelRenderable = ShapeFactory.makeCylinder(0.1f, 0.3f, Vector3.zero(), material);
                            modelRenderable.setShadowCaster(false);
                            modelRenderable.setShadowReceiver(false);
                        });
                break;
            case SPHERE:
                MaterialFactory.makeOpaqueWithColor(context, new Color(YELLOW))
                        .thenAccept(material -> {
                            modelRenderable = ShapeFactory.makeSphere(0.1f, Vector3.zero(), material);
                            modelRenderable.setShadowCaster(false);
                            modelRenderable.setShadowReceiver(false);
                        });
                break;

            case RESOURCE:
                ModelRenderable.builder()
                        // 애니메이션 모델 사용시 andy_dance 사용
                        .setSource(context, R.raw.andy_dance)
                        .build()
                        .thenAccept(renderable -> modelRenderable = renderable)
                        .exceptionally(throwable -> {
                            Log.e(TAG, "Unable to load object renderable");
                            return null;
                        });
                break;
        }
    }

    public void startRenderable() {
        modifyPlaneRenderer();
        buildObject(object_type);
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                if (modelRenderable == null) {
                    return;
                }
                // animation이 있는 Object는 1개만 사용가능함
                if (isAnimateObject && isAnimationObjectRunning) {
                    alertAnimObject();
                } else {
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    //참조 https://urbanbase.github.io/dev/2020/03/06/Sceneform-AR.html
                    TransformableNode andy = new TransformableNode(arFragment.getTransformationSystem());
                    andy.setParent(anchorNode);
                    andy.setRenderable(modelRenderable);
                    if (isAnimateObject) {
                        setObjectAnimation();
                        isAnimationObjectRunning = true;
                    }
                    andy.select();
                }
            }
        });

        //TODO remove object 로직 수정 필요
        removeObject();

    }

    // 이미지를 이용해 평면 렌더링, 바닥 이미지 변경
    private void modifyPlaneRenderer() {
        Texture.Sampler sampler = Texture.Sampler.builder()
                // texture 확대
                .setMagFilter(Texture.Sampler.MagFilter.LINEAR)
                //texture 축소
                .setMinFilter(Texture.Sampler.MinFilter.LINEAR)
                // texture 반복
                .setWrapMode(Texture.Sampler.WrapMode.REPEAT)
                .build();

        CompletableFuture<Texture> trigid = Texture.builder()
                .setSource(context, R.drawable.texture3)
                .setSampler(sampler)
                .build();

        PlaneRenderer planeRenderer = arFragment.getArSceneView().getPlaneRenderer();
        planeRenderer.getMaterial().thenAcceptBoth(trigid, ((material, texture) -> {
            material.setTexture(PlaneRenderer.MATERIAL_TEXTURE, texture);
//            material.setFloat(PlaneRenderer.MATERIAL_SPOTLIGHT_RADIUS, Float.MAX_VALUE);
        }));
    }

    private void setObjectAnimation() {
        AnimationData animationData = modelRenderable.getAnimationData("andy_dance");
        ModelAnimator modelAnimator = new ModelAnimator(animationData, modelRenderable);

        modelAnimator.start();
        modelAnimator.setRepeatCount(5);
    }

    private void stopAnimator() {
        AnimationData animationData = modelRenderable.getAnimationData("andy_dance");
        ModelAnimator modelAnimator = new ModelAnimator(animationData, modelRenderable);

        modelAnimator.end();
    }

    private void alertAnimObject() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Object Alert");
        builder.setMessage("animation Object can be used one");
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private void removeObject() {
        arFragment.getArSceneView().getScene().addOnPeekTouchListener(new Scene.OnPeekTouchListener() {
            @Override
            public void onPeekTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
                Log.i(TAG, "object touch");
                if (isDeleteMode) {
                    if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
                        return;
                    }

                    if (hitTestResult.getNode() != null) {
                        Node hitNode = hitTestResult.getNode();

                        if (hitNode.getRenderable() == modelRenderable) {
                            Log.i(TAG, "object selected");
                            if (isAnimateObject) {
                                stopAnimator();
                                initAnimProperty();
                            }
                            arFragment.getArSceneView().getScene().removeChild(hitNode);

                            hitNode.setParent(null);
                            hitNode = null;
                        }
                    }

                } else {
                    Log.i(TAG, "not Delete mode ");
                }
            }
        });
    }

    private void initAnimProperty() {
        if (AppData.getInstance().ObjectType == OBJECT_TYPE.RESOURCE) {
            isAnimateObject = true;
        } else {
            isAnimateObject = false;
        }
        isAnimationObjectRunning = false;
    }

    private void setAnimObjectProperty() {
        if (object_type != OBJECT_TYPE.RESOURCE) {
            isAnimateObject = false;
        } else {
            isAnimateObject = true;
        }
    }

}
