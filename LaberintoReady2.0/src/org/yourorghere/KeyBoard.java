/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author PC
 */
public class KeyBoard implements KeyListener {

    private GLCanvas canvas;
    public boolean[] keybuffer;
    //GL gl = drawable.getGL();
    GLU glu = new GLU();//Contiene funciones de mas alto nivel 

    public KeyBoard(GLCanvas canvas) {
        this.canvas = canvas;
        keybuffer = new boolean[256];
    }

    public void keyTyped(KeyEvent e) {
        //System.out.println(e);

        if (keybuffer['w']) {
            LaberintoReady20.camara.moveForward(-0.25f);
//            LaberintoReady20.avanzar = LaberintoReady20.avanzar - 0.5f;
        }

        if (keybuffer['s']) {
            LaberintoReady20.camara.moveForward(0.25f);
//            LaberintoReady20.avanzar = LaberintoReady20.avanzar + 0.5f;
        }

        if (keybuffer['a']) {
            LaberintoReady20.camara.rotateY(25);
//            LaberintoReady20.girar = LaberintoReady20.girar + 10;
        }

        if (keybuffer['d']) {
            LaberintoReady20.camara.rotateY(-25);
//            LaberintoReady20.girar = LaberintoReady20.girar - 10;
        }
        if (e.getKeyChar() == 'v') {
            if (LaberintoReady20.mirar == false) {
                LaberintoReady20.mirar = true;
            } else {
                LaberintoReady20.mirar = false;
            }
        }

    }

    public void keyPressed(KeyEvent e) {
        keybuffer[e.getKeyChar()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keybuffer[e.getKeyChar()] = false;
    }

}
