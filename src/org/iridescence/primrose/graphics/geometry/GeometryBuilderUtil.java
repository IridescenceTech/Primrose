package org.iridescence.primrose.graphics.geometry;

public class GeometryBuilderUtil {

    public static Geometry createPlane(float width, float height){

        int[] indices = new int[]{
                0, 1, 2, 2, 3, 0
        };
        float[] uvs = new float[]{
                0.f, 0.f,
                1.f, 0.f,
                1.f, 1.f,
                0.f, 1.f
        };
        float[] positions = new float[]{
                0, 0, 0.f,
                width, 0, 0.f,
                width, height, 0.f,
                0, height, 0.f
        };
        float[] normals = new float[]{
                0, 0, -1,
                0, 0, -1,
                0, 0, -1,
                0, 0, -1
        };

        return new Geometry(positions, uvs, normals, indices);
    }

    public static Geometry createCube(float radius){

        int[] indices = new int[]{
                //Front
                0, 1, 2, 2, 3, 0,
                //Back
                4, 5, 6, 6, 7, 4,

                8, 9, 10, 10, 11, 8,
                12, 13, 14, 14, 15, 12,
                16, 17, 18, 18, 19, 16,
                20, 21, 22, 22, 23, 20
        };
        float[] uvs = new float[]{
                //Front
                0.f, 0.f,
                1.f, 0.f,
                1.f, 1.f,
                0.f, 1.f,

                //Back
                0.f, 0.f,
                0.f, 1.f,
                1.f, 1.f,
                1.f, 0.f,

                //Left
                0.f, 0.f,
                0.f, 1.f,
                1.f, 1.f,
                1.f, 0.f,

                //Right
                0.f, 0.f,
                1.f, 0.f,
                1.f, 1.f,
                0.f, 1.f,

                //Top
                0.f, 0.f,
                1.f, 0.f,
                1.f, 1.f,
                0.f, 1.f,

                //Bottom
                0.f, 0.f,
                0.f, 1.f,
                1.f, 1.f,
                1.f, 0.f,
        };
        float[] positions = new float[]{

                //Front
                0, 0, 0.f,
                radius, 0, 0.f,
                radius, radius, 0.f,
                0, radius, 0.f,

                //Back
                0, 0, -radius,
                0, radius, -radius,
                radius, radius, -radius,
                radius, 0, -radius,

                //Left
                0, 0, 0,
                0, radius, 0,
                0, radius, -radius,
                0, 0, -radius,

                //Right
                radius, 0, 0,
                radius, 0, -radius,
                radius, radius, -radius,
                radius, radius, 0,

                //Top
                0, radius, 0,
                radius, radius, 0,
                radius, radius, -radius,
                0, radius, -radius,

                //Bottom
                0, 0, 0,
                0, 0, -radius,
                radius, 0, -radius,
                radius, 0, 0,
        };
        float[] normals = new float[]{
                0, 0, -1,
                0, 0, -1,
                0, 0, -1,
                0, 0, -1,

                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1,

                1, 0, 0,
                1, 0, 0,
                1, 0, 0,
                1, 0, 0,

                -1, 0, 0,
                -1, 0, 0,
                -1, 0, 0,
                -1, 0, 0,

                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,

                0, -1, 0,
                0, -1, 0,
                0, -1, 0,
                0, -1, 0,
        };

        Geometry result = new Geometry(positions, uvs, normals, indices);
        result.unbindVAO();
        return result;
    }
}
