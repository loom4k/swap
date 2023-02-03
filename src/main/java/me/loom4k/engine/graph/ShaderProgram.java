package me.loom4k.engine.graph;

import me.loom4k.engine.Utils;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class ShaderProgram {
    private final int programId;

    // The ShaderProgram constructor receives a list of ShaderModuleData instances which define
    // the shader module type (vertex, fragment, etc.) and the path to th source file which
    // contains the shader module code. The constructor starts by creating a new OpenGL shader
    // program by compiling first each shader module (by invoking the createShader method) and
    // finally linking all together (by invoking the link method). Once the shader program has
    // been linking, the compiled vertex and fragment shaders can be freed up (by calling
    // glDetachShader)
    public ShaderProgram(List<ShaderModuleData> shaderModuleDataList) {
        programId = glCreateProgram();
        if(programId == 0) {
            throw new RuntimeException("Could not create shader");
        }

        List<Integer> shaderModules = new ArrayList<>();
        shaderModuleDataList.forEach(s -> shaderModules.add(createShader(Utils.readFile(s.shaderFile), s.shaderType)));

        link(shaderModules);
    }

    public void bind() {
        glUseProgram(programId);
    }

    public void cleanup() {
        unbind();
        if(programId != 0) {
            glDeleteProgram(programId);
        }
    }

    protected int createShader(String shaderCode, int shaderType) {
        int shaderId = glCreateShader(shaderType);
        if(shaderId == 0) {
            throw new RuntimeException("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if(glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException("Error compiling shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    public int getProgramId() {
        return programId;
    }

    private void link(List<Integer> shaderModules) {
        glLinkProgram(programId);
        if(glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new RuntimeException("Error linking shader code: " + glGetProgramInfoLog(programId, 1024));
        }

        shaderModules.forEach(s -> glDetachShader(programId, s));
        shaderModules.forEach(GL30::glDeleteShader);
    }

    public void unbind() {
        glUseProgram(0);
    }

    // This function is used for debugging and shouldn't be used during production release. It validates
    // if the game shader is correct given the current OpenGL state. This means that validation may fail
    // in some cases even if the shader is correct, due to the fact that the current state is not complete
    // enough to run the shader (some data might have not been uploaded yet). Should be called when all
    // required input and output data is properly bound.
    public void validate() {
        glValidateProgram(programId);
        if(glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            throw new RuntimeException("Error validating shader code: " + glGetProgramInfoLog(programId, 1024));
        }
    }

    public record ShaderModuleData(String shaderFile, int shaderType) {

    }
}
