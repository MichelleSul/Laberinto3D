/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

/**
 * esta clase realiza las operacione slementales entre vectores
 * @author SONY
 */
public class Vector3 {
    float X,Y,Z;

    
     public Vector3() {
        this.X = 0;
        this.Y = 0;
        this.Z = 0;
    }
     
    public Vector3(float x, float y, float z) {
        this.X = x;
        this.Y = y;
        this.Z = z;
    }
    
    
    /**
     * 
     * @return el vector inverso de (x,y,z)
     */
    public Vector3 getInverse()
    {
        return new Vector3(-X, -Y, -Z);
    }

    /**
     * 
     * @return tamaño del vector3
     */
    public float getLength()
    {
        return (float)Math.sqrt((X * X) + (Y * Y) + (Z * Z));
    }

    /**
     * 
     * @return vector normal 
     */
    public Vector3 getNormal()
    {
        float l = getLength();

        if(l == 0.0f)
            return null;

        return new Vector3(X / l, Y / l, Z / l);
    }
    
    
    /**
     * calcula la suma de vectores
     * @param u
     * @param v
     * @return suma de u con v
     */
    public static Vector3 vector3Addition(Vector3 u, Vector3 v){
        Vector3 w = new Vector3();
        w.X = u.X + v.X;
        w.Y = u.Y + v.Y;
        w.Z = u.Z + v.Z;
        return w;
    }
    
    
     public static Vector3 vector3Substraction(Vector3 u, Vector3 v)
    {
        Vector3 w = new Vector3();

        w.X = v.X - u.X;
        w.Y = v.Y - u.Y;
        w.Z = v.Z - u.Z;

        return w;
    }
     
     
    /**
     * calcula la multiplicacion de un vector u con un escalar r
     * @param u vector3
     * @param r float
     * @return ru
     */
    public static Vector3 vector3Multiplication(Vector3 u, float r){
        Vector3 w = new Vector3();

        w.X = u.X * r;
        w.Y = u.Y * r;
        w.Z = u.Z * r;

        return w;
    }
    
    
    /**
     * calcula el producto cruz entre dos vectores
     * @param u Vector3
     * @param v Vector3
     * @return uxv
     */
    public static Vector3 vector3CrossProduct(Vector3 u, Vector3 v){
        Vector3 w = new Vector3();

        w.X = u.Y * v.Z - u.Z * v.Y;
        w.Y = u.Z * v.X - u.X * v.Z;
        w.Z = u.X * v.Y - u.Y * v.X;

        return w;
    }

    /**
     * producto punto entre dos  vectores
     * @param u Vector3
     * @param v Vector3
     * @return u.v
     */
    public static float vector3DotProduct(Vector3 u, Vector3 v){
        return (v.X * u.X) + (v.Y * u.Y) + (v.Z * u.Z);
    }
    
    
    
}
