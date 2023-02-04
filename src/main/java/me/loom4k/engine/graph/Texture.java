package me.loom4k.engine.graph;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
    private int textureId;
    private String texturePath;

    public Texture(String texturePath) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            this.texturePath = texturePath;
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            // This function loads the image in the ByteBuffer. It requires a parameter called desired_channels. When 4
            // desired_channels is inputted, it means that the texture will work with RGBA (A for Alpha or opacity)
            // OpenGL also requires the texture to have a size of a power of two (2, 4, 8, 16, ...)
            ByteBuffer buffer = stbi_load(texturePath, w, h, channels, 4);
            if(buffer == null) {
                throw new RuntimeException("Image file [" + texturePath + "] not loaded: " + stbi_failure_reason());
            }

            int width = w.get();
            int height = h.get();

            generateTexture(width, height, buffer);

            stbi_image_free(buffer);
        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public void cleanup() {
        glDeleteTextures(textureId);
    }

    public void generateTexture(int width, int height, ByteBuffer buffer) {
        textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        // The glTexParameteri functions says that when a pixel is drawn with no direct one-to-one association to a
        // texture coordinate it will pick the nearest texture coordinate point
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0,
                GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        // A mipmap is a decreasing resolution set of images generated from a high detailed texture. These lower
        // resolution images will be used when the object is scaled
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public String getTexturePath() {
        return texturePath;
    }

}
