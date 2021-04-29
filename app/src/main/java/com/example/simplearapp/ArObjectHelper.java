package com.example.simplearapp;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
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
    Context context;

    ArFragment arFragment;
    ModelRenderable modelRenderable;
    OBJECT_TYPE object_type;

    public enum OBJECT_TYPE {
        CUBE,
        CYLINDER,
        SPHERE,
        RESOURCE
    }

    public ArObjectHelper(Context context, ArFragment arFragment, OBJECT_TYPE object_type) {
        this.context = context;
        this.arFragment = arFragment;
        this.object_type = object_type;
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
                        .setSource(context, R.raw.andy_dance)
                        .build()
                        .thenAccept(renderable -> modelRenderable = renderable)
                        .exceptionally(throwable -> {
                            Log.e(TAG, "Unable to load andy renderable");
                            return null;
                        });
                break;
        }
    }

    public void setRenderable() {
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                if (modelRenderable == null) {
                    return;
                }
                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());

                //참조 https://urbanbase.github.io/dev/2020/03/06/Sceneform-AR.html
                TransformableNode andy = new TransformableNode(arFragment.getTransformationSystem());
                andy.setParent(anchorNode);
                andy.setRenderable(modelRenderable);
                setObjectAnimation(modelRenderable);
                andy.select();
            }
        });
        modifyPlaneRenderer();
    }

    // 바닥 렌더링 커스텀
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

    private void setObjectAnimation(ModelRenderable modelRenderable) {
        AnimationData animationData = modelRenderable.getAnimationData("andy_dance");
        Log.i(TAG, animationData.getName());
        ModelAnimator modelAnimator = new ModelAnimator(animationData, modelRenderable);

        modelAnimator.start();
        modelAnimator.setRepeatCount(5);
    }
}
