package Engine.Helper.Loader;

import Engine.System.Graphics.Component.Mesh3D;
import Engine.Utils;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class OBJLoader {
    /**
     * Gathers the data about the three vertices of a triangular face.
     */
    protected static class Face {
        /**
         * Each vertex of the face may have an associate texture coordinate and normal vector.
         */
        private IndicesGroup[] indicesGroups = new IndicesGroup[3];

        Face(String vertex1, String vertex2, String vertex3) {
            indicesGroups = new IndicesGroup[3];

            indicesGroups[0] = parseLine(vertex1);
            indicesGroups[1] = parseLine(vertex2);
            indicesGroups[2] = parseLine(vertex3);
        }

        /**
         * Parses a line from the object file.
         *
         * @param line The line to parse.
         * @return The group of indices for the current vertex concerned by the line.
         */
        private IndicesGroup parseLine(String line) {
            IndicesGroup indicesGroup = new IndicesGroup();

            String[] lineTokens = line.split("/");
            int length = lineTokens.length;
            indicesGroup.vertexIndex = Integer.parseInt(lineTokens[0]) - 1;

            if (length > 1) {
                // There is a texture coordinate index.
                String textureCoordinate = lineTokens[1];

                indicesGroup.textureCoordinateIndex =
                        textureCoordinate.length() > 0
                                ? Integer.parseInt(textureCoordinate) - 1
                                : IndicesGroup.NO_VALUE;

                if (length > 2) {
                    // There is a normal vector index.
                    indicesGroup.normalVectorIndex = Integer.parseInt(lineTokens[2]) - 1;
                }
            }

            return indicesGroup;
        }

        /**
         * @return An array containing the groups of indices for each vertex of the face.
         */
        IndicesGroup[] getFaceVertexIndices() {
            return indicesGroups;
        }
    }

    /**
     * Loads a mesh described by an object file.
     *
     * @param fileName The path of the file.
     * @return An abstract description of the mesh described by the file, unattached to an entity.
     * @throws Exception The file could not be found.
     */
    public static Mesh3D loadMesh(String fileName) throws Exception {
        // Read all the lines from the source object file.
        List<String> lines = Utils.readAllLines(fileName);

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textureCoordinates = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Face> faces = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");

            switch (tokens[0]) {
                case "v":
                    // Geometric vertex.
                    vertices.add(new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])));
                    break;
                case "vt":
                    // Texture coordinate.
                    textureCoordinates.add(new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2])));
                    break;
                case "vn":
                    // Vertex normal.
                    normals.add(new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])));
                    break;
                case "f":
                    faces.add(new Face(tokens[1], tokens[2], tokens[3]));
                    break;
                default:
                    // Ignore other lines.
                    break;
            }
        }

        return castListsToArrays(vertices, textureCoordinates, normals, faces);
    }

    /**
     * Casts the lists parsed in the file into arrays to match with the signature of meshes.
     *
     * @param vertices              A list of the vertices of the mesh.
     * @param textureCoordinates    A list of texture coordinates of the mesh.
     * @param normals               A list of normals of the mesh.
     * @param faces                 A list of the triangular faces of the mesh.
     * @return                      A mesh matching with the description of the initial object file.
     */
    private static Mesh3D castListsToArrays(List<Vector3f> vertices, List<Vector2f> textureCoordinates,
                                            List<Vector3f> normals, List<Face> faces) {
        List<Integer> indices = new ArrayList<>();

        // Create an array of vertices to feat the definition of a mesh.
        float[] treatedVertices = new float[vertices.size() * 3];
        int i = 0;
        for (Vector3f vertex : vertices) {
            treatedVertices[i * 3] = vertex.x;
            treatedVertices[i * 3 + 1] = vertex.y;
            treatedVertices[i * 3 + 2] = vertex.z;
            i++;
        }

        // Create arrays for texture coordinates and normals.
        float[] treatedTextureCoordinates = new float[vertices.size() * 2];
        float[] treatedNormals = new float[vertices.size() * 3];

        // Process each face of the mesh.
        for (Face face : faces) {
            for (IndicesGroup currentIndex : face.getFaceVertexIndices()) {
                processFaceVertex(currentIndex, textureCoordinates, normals,
                        indices, treatedTextureCoordinates, treatedNormals);
            }
        }

        // Cast the indices list to an array of integers, ready to be applied to the mesh.
        int[] treatedIndices = indices.stream().mapToInt((Integer v) -> v).toArray();

        return new Mesh3D(null, treatedVertices, treatedIndices, treatedNormals, treatedTextureCoordinates);
    }

    private static void processFaceVertex(IndicesGroup indicesGroup, List<Vector2f> textureCoordinates,
                                          List<Vector3f> normals, List<Integer> indices,
                                          float[] treatedTextureCoordinates, float[] treatedNormals) {
        int vertexIndex = indicesGroup.vertexIndex;
        indices.add(vertexIndex);

        if (indicesGroup.textureCoordinateIndex >= 0) {
            treatedTextureCoordinates[vertexIndex * 2] =
                    textureCoordinates.get(indicesGroup.textureCoordinateIndex).x;
            treatedTextureCoordinates[vertexIndex * 2 + 1] =
                    1 - textureCoordinates.get(indicesGroup.textureCoordinateIndex).y;
        }

        if (indicesGroup.normalVectorIndex >= 0) {
            treatedNormals[vertexIndex * 3] = normals.get(indicesGroup.normalVectorIndex).x;
            treatedNormals[vertexIndex * 3 + 1] = normals.get(indicesGroup.normalVectorIndex).y;
            treatedNormals[vertexIndex * 3 + 2] = normals.get(indicesGroup.normalVectorIndex).z;
        }
    }

    /**
     * Gathers the indices of a vertex and its associated texture coordinate and normal vector index.
     */
    static class IndicesGroup {
        /**
         * The default value given to indices when the object goes not provide one.
         */
        static final int NO_VALUE = -1;

        int vertexIndex;

        int textureCoordinateIndex;

        int normalVectorIndex;

        IndicesGroup() {
            vertexIndex = NO_VALUE;
            textureCoordinateIndex = NO_VALUE;
            normalVectorIndex = NO_VALUE;
        }
    }
}
