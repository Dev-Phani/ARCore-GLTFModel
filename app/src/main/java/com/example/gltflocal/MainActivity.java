package com.example.gltflocal;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
//    private String Asset_3D= "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Avocado/glTF/Avocado.gltf";
//private String Asset_3D= "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/DamagedHelmet/glTF/DamagedHelmet.gltf";
private String Asset_3D= "models/DamagedHelmet.gltf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        //arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            Anchor anchor = hitResult.createAnchor();

            RenderableSource renderableSource = RenderableSource
                    .builder()
                    .setSource(this, Uri.parse(Asset_3D), RenderableSource.SourceType.GLTF2)
                    .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                    .build();

            ModelRenderable
                    .builder()
                    .setSource(this, renderableSource)
                    .setRegistryId(Asset_3D)
                    .build()
                    .thenAccept(modelRenderable -> {
                        placeModel(modelRenderable,anchor);
//                    Toast.makeText(this, "Model built", Toast.LENGTH_SHORT).show();;
//                    renderable = modelRenderable;
                    });


        });




    }

    private void placeModel(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
//        transformableNode.setLocalScale(new Vector3(0.1f, 0.1f, 0.1f));
//        transformableNode.setParent(anchorNode);
//        transformableNode.setRenderable(modelRenderable);
        // transformableNode.getScaleController().setMaxScale(0.02f);
        //transformableNode.getScaleController().setMinScale(0.01f);
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        //anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }
}