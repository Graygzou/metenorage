package Editor;

/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyInputConsumer;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglInputSystem;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglRenderDevice;
import de.lessvoid.nifty.renderer.lwjgl.time.LWJGLTimeProvider;
import de.lessvoid.nifty.spi.input.InputSystem;
import de.lessvoid.nifty.tools.resourceloader.NiftyResourceLoader;
import org.lwjgl.system.*;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/** AWT integration demo using jawt. */
public final class Test {

    private Test() {
    }

    public static void main(String[] args) {
        if (Platform.get() != Platform.WINDOWS) {
            throw new UnsupportedOperationException("This demo can only run on Windows.");
        }


        // load GUI from file
        /*
        nifty.addXml("res/xml/IntroScreen.xml");
        nifty.addXml("res/xml/GameScreen.xml");
        nifty.addXml("res/xml/EndScreen.xml");*/
    }

}
