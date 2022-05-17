/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

/**
 *
 * @author SONY
 */
public class Camara {

    //donde se va aposicionar inicialmente la camara
    Vector3 origViewDir = new Vector3(3.5f, 0.0f, -3.0f);
    Vector3 origRightVector = new Vector3(0.0f, 0.0f, 0.0f);
    Vector3 origUpVector = new Vector3(0.0f, 0.5f, 0.0f);
    Vector3 origPosition = new Vector3(3.5f, 0.5f, 0.0f);

    //nuevas posiciones
    Vector3 viewDir;   // direccion de la vista
    Vector3 rightVector;
    Vector3 upVector;//vector de la camara
    Vector3 position;

    //rotacion de la camara
    float rotatedX;
    float rotatedY;
    float rotatedZ;

    float PiDiv180 = (float) Math.PI / 180.0f;   // para convertir grados en radianes

    /**
     * constructor de la clase
     */
    public Camara() {
        reset();//restaura la camara a sus valores de fabrica
    }

    /**
     * vuelve la camara a sus valores de fabrica
     */
    public void reset() {
        viewDir = origViewDir;
        rightVector = origRightVector;
        upVector = origUpVector;
        position = origPosition;
        viewDir = viewDir.getNormal();

        rotatedX = 0.0f;
        rotatedY = 0.0f;
        rotatedZ = 0.0f;
    }

    /**
     * desplazamiento de la cámara en función de la dirección
     *
     * @param direction direccion de la camara
     */
    public void move(Vector3 direction) {
        //sumamos la posicion actual de la camra con la nueva direccion
        position = Vector3.vector3Addition(position, direction);
    }

    /**
     * rotacion de la camara en el eje x
     *
     * @param angle angulo de rotacion
     */
    public void rotateX(float angle) {

        rotatedX += angle;
        //tenemos que pasar los grados a radianes
        Vector3 temp1 = Vector3.vector3Multiplication(viewDir, (float) Math.cos(angle * PiDiv180));
        Vector3 temp2 = Vector3.vector3Multiplication(upVector, (float) Math.sin(angle * PiDiv180));

        viewDir = Vector3.vector3Addition(temp1, temp2).getNormal();
        upVector = Vector3.vector3Multiplication(Vector3.vector3CrossProduct(viewDir, rightVector), -1.0f);
    }

    /**
     * rotacion en y de la camara
     *
     * @param angle angulo de rotacion
     */
    public void rotateY(float angle) {
        rotatedY += angle;

        Vector3 temp1 = Vector3.vector3Multiplication(viewDir, (float) Math.cos(angle * PiDiv180));
        Vector3 temp2 = Vector3.vector3Multiplication(rightVector, (float) Math.sin(angle * PiDiv180));

        viewDir = Vector3.vector3Substraction(temp2, temp1);
        viewDir = viewDir.getNormal();
        rightVector = Vector3.vector3CrossProduct(viewDir, upVector);
    }

    public void rotateZ(float angle) {
        rotatedZ += angle;

        Vector3 temp1 = Vector3.vector3Multiplication(rightVector, (float) Math.cos(angle * PiDiv180));
        Vector3 temp2 = Vector3.vector3Multiplication(upVector, (float) Math.sin(angle * PiDiv180));

        rightVector = Vector3.vector3Addition(temp1, temp2).getNormal();
        upVector = Vector3.vector3Multiplication(Vector3.vector3CrossProduct(viewDir, rightVector), -1.0f);
    }

    /**
     * mover hacia adelante
     *
     * @param distance
     */
    public void moveForward(float distance) {
        //utilizo menos ya que la corrdenada en negativa en el eje z es hacia adelante
        position = Vector3.vector3Addition(position, Vector3.vector3Multiplication(viewDir, -distance));
    }

    /**
     * para mover la camara a la izquierda o la derecha
     *
     * @param distance
     */
    public void strafeRight(float distance) {
        position = Vector3.vector3Addition(position, Vector3.vector3Multiplication(rightVector, distance));
    }

    /**
     * movimiento hacia arriba o hacia abajo de la camara
     *
     * @param distance
     */
    public void moveUpward(float distance) {
        position = Vector3.vector3Addition(position, Vector3.vector3Multiplication(upVector, distance));
    }

    /**
     * posicion de la camara
     *
     * @return
     */
    public Vector3 getCameraPosition() {
        return position;
    }

    /**
     * consegir el objetivo de la camara(hacia donde esta el punto de interes)
     *
     * @return
     */
    public Vector3 getCameraTarget() {
        return Vector3.vector3Addition(position, viewDir);
    }

    /**
     * consegir el vector de la camara
     *
     * @return
     */
    public Vector3 getUpVector() {
        return upVector;
    }

}
