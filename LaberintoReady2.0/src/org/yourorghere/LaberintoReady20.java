package org.yourorghere;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 * LaberintoReady.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel)
 * <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class LaberintoReady20 implements GLEventListener {

    static boolean mirar = true, winner = false;
    static Camara camara = new Camara();
    public static float avanzar = 3.5f, retroceder = 3.5f, girar = 0;
    GLUquadric qobj;
    int startList;
    private Texture pared, piso, ganador;
//    private Pared pared;
    private static GL gl;
    private GLUT glut = new GLUT();
    private GLU glu;

    public static void main(String[] args) {
        Frame frame = new Frame("Laberinto Ready");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new LaberintoReady20());
        frame.add(canvas);
        frame.setSize(1024, 768);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
        ////
        KeyBoard keylistener = new KeyBoard(canvas);
        canvas.addKeyListener(keylistener);
        ////
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        try {
            pared = TextureIO.newTexture(new File("pared.jpg"), true);
            piso = TextureIO.newTexture(new File("piso.jpg"), true);
            ganador = TextureIO.newTexture(new File("ganador.jpg"), true);

        } catch (IOException e) {
            System.err.println("No se puede cargar textura " + e);
//            System.exit(1);
        }
        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 0.1f, 2000.0); //CAMBIAR A UN VALOR MAYOR TANTO DE LEJOS COM ODE CERCA
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glLoadIdentity();

    }

    public void display(GLAutoDrawable drawable) {
        gl = drawable.getGL();
        GLU glu = new GLU();
        float[] difuse = {0.7f, 0.8f, 0.9f, 1};
        float[] specular = {0.7f, 0.8f, 0.1f, 1};
        float[] position = {3.5f, 0, -2.5f, 1};
        float[] spotDir = {0, 1, 0};
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();
        ///////
                Vector3 camPosition = camara.getCameraPosition();
                Vector3 camTarget = camara.getCameraTarget();
                Vector3 upVector = camara.getUpVector();
                if (winner == false) {
                    if (mirar == true) {
                        glu.gluLookAt(camPosition.X,
                                camPosition.Y,
                                camPosition.Z,
                                camTarget.X,
                                camTarget.Y,
                                camTarget.Z,
                                upVector.X,
                                upVector.Y,
                                upVector.Z);
                    
                
        //                
                        if (camPosition.X >= 3 && camPosition.X <= 4) {
                            if (camPosition.Z >= -3 && camPosition.Z <= -2) {
                                winner = true;
                            }
                        }
                    } else {
        
                        glu.gluLookAt(-4, 10, 4, 3, -2.5f, -3, 0, 1, 0);
                    }
                }
        
                if (camPosition.X >= 3 && camPosition.X <= 4) {
                    if (camPosition.Z >= -3 && camPosition.Z <= -2) {
        
                        glu.gluLookAt(3.5f, 0.5f, -6, 3.5f, 0.42f, -7, 0, 1, 0);
                        System.out.println("GANADOR");
                        gl.glColor3f(1, 1, 1);
                        ganador.enable();
                        ganador.bind();
                        gl.glBegin(GL.GL_QUADS);
                        {
                            gl.glTexCoord2f(0, 1);
                            gl.glVertex3f(3, 0, -7);
                            gl.glTexCoord2f(1, 1);
                            gl.glVertex3f(4, 0, -7);
                            gl.glTexCoord2f(1, 0);
                            gl.glVertex3f(4, 0.8f, -7);
                            gl.glTexCoord2f(0, 0);
                            gl.glVertex3f(3, 0.8f, -7);
                        }
                        gl.glEnd();
                        ganador.disable();
                    }else{
                        winner = false;
                    }
                }
        //
        //        /////////
                if (mirar == false) {
                    gl.glPushMatrix();
                    {
                        gl.glTranslatef(camPosition.X, camPosition.Y, camPosition.Z);
                        System.out.println(camPosition.X + " -- " + camPosition.Y + " -- " + camPosition.Z);
                        gl.glScalef(1, 2, 1);
//                        gl.glColor3f(0.5f, 0.5f, 1);
                        glut.glutSolidCube(0.2f);
                    }
                    gl.glPopMatrix();
                }
        //        ////////
        //
        //        ////////
                gl.glPushMatrix();
                {
                    gl.glEnable(GL.GL_LIGHTING);
                    gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, difuse, 0);
                    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, difuse, 0);
                    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
                    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0);
        
                    gl.glLightf(GL.GL_LIGHT0, GL.GL_SPOT_CUTOFF, 20);
                    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPOT_DIRECTION, spotDir, 0);
                    gl.glLightf(GL.GL_LIGHT0, GL.GL_SPOT_EXPONENT, 2);
                    gl.glEnable(GL.GL_LIGHT0);
                    gl.glEnable(GL.GL_COLOR_MATERIAL);
                }
                gl.glPopMatrix();
                glu.gluLookAt(-4, 10, 4, 3, 0, -3, 0, 1, 0);
        //
                gl.glPushMatrix();
                {
//                    gl.glColor3f(1, 1, 1);
                    dibujarPiso();
                    verParedes();
                    horParedes();
                }
                gl.glPopMatrix();
        //
                gl.glPushMatrix();
                {
                    gl.glTranslatef(3.5f, 0.5f, -2.5f);
                    gl.glRotatef(girar, 0, 1, 0);
                    gl.glColor3f(1, 0.6f, 0);
                    glut.glutSolidTeapot(0.1f);
                }
                gl.glPopMatrix();
                gl.glPushMatrix();
                {
                    gl.glTranslatef(3.5f, 0.1f, -2.5f);
                    gl.glRotatef(90, 0, 1, 0);
                    gl.glColor3f(1, 1, 1);
                    glut.glutSolidCube(0.1f);
                }
                gl.glPopMatrix();
                girar += 0.2f;
                
                gl.glFlush();
    }
        //        ///////

            public void dibujarParedHorizontal(int x, int z) {
                pared.enable();
                pared.bind();
                gl.glBegin(GL.GL_QUADS);
                {
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(x, 0, z);
                    gl.glTexCoord2f(1, 0);
                    gl.glVertex3f(x + 1, 0, z);
                    gl.glTexCoord2f(1, 1);
                    gl.glVertex3f(x + 1, 1, z);
                    gl.glTexCoord2f(0, 1);
                    gl.glVertex3f(x, 1, z);
                }
                gl.glEnd();
                pared.disable();
        
            }
        //
            public void dibujarParedVertical(int x, int z) {
                pared.enable();
                pared.bind();
                gl.glBegin(GL.GL_QUADS);
                {
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(x, 0, z);
                    gl.glTexCoord2f(1, 0);
                    gl.glVertex3f(x, 0, z - 1);
                    gl.glTexCoord2f(1, 1);
                    gl.glVertex3f(x, 1, z - 1);
                    gl.glTexCoord2f(0, 1);
                    gl.glVertex3f(x, 1, z);
                }
                gl.glEnd();
                pared.disable();
            }
        //
            public void horParedes() {
                //primera fila
                for (int i = 0; i < 7; i++) {
                    if (i != 3) {
                        dibujarParedHorizontal(i, 0);
                    }
                }
                //segunda fila
                dibujarParedHorizontal(2, -1);
                //tercera fila
                for (int i = 1; i < 5; i++) {
                    dibujarParedHorizontal(i, -2);
                }
                //cuarta fila
                dibujarParedHorizontal(1, -3);
                dibujarParedHorizontal(6, -3);
                //quinta fila
                dibujarParedHorizontal(0, -4);
                dibujarParedHorizontal(3, -4);
                dibujarParedHorizontal(4, -4);
                //sexta fila
                dibujarParedHorizontal(1, -5);
                dibujarParedHorizontal(5, -5);
                dibujarParedHorizontal(6, -5);
                //septima fila
                for (int i = 0; i < 7; i++) {
                    dibujarParedHorizontal(i, -6);
                }
        
                ///////
            }
        //
            public void dibujarPiso() {
                piso.enable();
                piso.bind();
                gl.glBegin(GL.GL_QUADS);
                {
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(0, 0, 0);
                    gl.glTexCoord2f(1, 0);
                    gl.glVertex3f(7, 0, 0);
                    gl.glTexCoord2f(1, 1);
                    gl.glVertex3f(7, 0, -6);
                    gl.glTexCoord2f(0, 1);
                    gl.glVertex3f(0, 0, -6);
                }
                gl.glEnd();
                piso.disable();
            }
        //
            private void verParedes() {
                //primera columna de izq a derecha
                for (int i = 0; i > -6; i--) {
                    dibujarParedVertical(0, i);
                }
                //segunda columna
                dibujarParedVertical(1, -1);
                dibujarParedVertical(1, -3);
                dibujarParedVertical(1, -4);
                //tercera columna
                dibujarParedVertical(2, -4);
                //cuarta columna
                dibujarParedVertical(3, 0);
                dibujarParedVertical(3, -2);
                dibujarParedVertical(3, -5);
                //quinta columna
                dibujarParedVertical(4, -1);
                dibujarParedVertical(4, -2);
                dibujarParedVertical(4, -3);
                dibujarParedVertical(4, -4);
                //sexta columna
                dibujarParedVertical(5, -1);
                dibujarParedVertical(5, -3);
                //septima columna        
                dibujarParedVertical(6, 0);
                dibujarParedVertical(6, -1);
                dibujarParedVertical(6, -3);
                //ultima columna
                for (int i = 0; i > -6; i--) {
                    dibujarParedVertical(7, i);
                }
        
            }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

}
