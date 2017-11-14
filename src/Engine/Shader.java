package Engine;

import static org.lwjgl.opengl.GL20.*;

/**
 * @author : Matthieu Le Boucher <matt.leboucher@gmail.com>
 */
public class Shader {

    private final int programId;

    private int vertexShaderId;

    private int fragmentShaderId;

    public Shader() throws Exception {
        programId = glCreateProgram();

        if (programId == 0)
            throw new Exception("Metenorage: could not create shader program.");
    }

    public void createVertexShader(String shaderCode) throws Exception {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    private int createShader(String shaderCode, int shaderType) throws Exception {
        // Attempt creating a shader program.
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0)
            throw new Exception("An error occured while creating shader of type: " + shaderType);

        // Load and compile the shader source.
        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0)
            throw new Exception("An error occured when compiling shader code: "
                    + glGetShaderInfoLog(shaderId, 1024));

        // Attach the shader to the program.
        glAttachShader(programId, shaderId);

        return shaderId;
    }

    public void link() throws Exception {
        glLinkProgram(programId);

        if (glGetProgrami(programId, GL_LINK_STATUS) == 0)
            throw new Exception("An error occurred while linking shader code: "
                    + glGetProgramInfoLog(programId, 1024));

        if (fragmentShaderId != 0)
            glDetachShader(programId, fragmentShaderId);

        if (vertexShaderId != 0)
            glDetachShader(programId, vertexShaderId);

        glValidateProgram(programId);

        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0)
            System.err.println("Warning validating shader code: "
                    + glGetProgramInfoLog(programId, 1024));
    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();

        if (programId != 0)
            glDeleteProgram(programId);
    }
}