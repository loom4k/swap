package me.loom4k.engine.graph;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private int numVertices;

    // Vertex Array Objects (VAO). Wrapper that groups a set of definitions for the
    // data that is going to be stored in the graphics card
    private int vaoId;

    // Vertex Buffer Object (VBO). Memory buffers stored in the graphics card memory
    // that stores vertices
    private List<Integer> vboIdList;

    public Mesh(float[] positions, float[] colors, int[] indices) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            numVertices = indices.length;
            vboIdList = new ArrayList<>();

            // These functions generate the VAO and bind it
            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            // These functions generate the VBO and put the data into it. To do so, a FloatBuffer is created. This
            // is mainly due to the fact that the code has to interface with the OpenGL library, which is C based,
            // so it has to transform the array of floats into something that can be managed by the library

            // The code uses the MemoryUtil class to create a buffer in off-heap memory so that it's accessible by
            // the OpenGL library. After it stores the data (put method), it resets the position of the buffer to 0
            // with the flip method
            int vboId = glGenBuffers();
            vboIdList.add(vboId);
            FloatBuffer positionsBuffer = stack.callocFloat(positions.length);
            positionsBuffer.put(0, positions);

            // This then binds the VBO and loads the data into it. It then defines the structure of our data and
            // stores it in one of the attribute lists of the VAO (glVertexAttribPointer function)
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(0);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // COLOR VBO
            // The previous two blocks are repeated with some minor changed to do Index VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            FloatBuffer colorsBuffer = stack.callocFloat(colors.length);
            colorsBuffer.put(0, colors);
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(1);
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

            // INDEX VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            IntBuffer indicesBuffer = stack.callocInt(indices.length);
            indicesBuffer.put(0, indices);

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }
    }

    // This function clears all the VAO and VBO from memory
    public void cleanup() {
        vboIdList.stream().forEach(GL30::glDeleteBuffers);
        glDeleteVertexArrays(vaoId);
    }

    public int getNumVertices() {
        return numVertices;
    }

    public final int getVaoId() {
        return vaoId;
    }

}
