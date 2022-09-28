/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Tomi.Biblioteca.Errores;

/**
 *
 * @author Mi Pc
 */
public class ErrorWeb extends Exception {

    /**
     * Creates a new instance of <code>ErrorWeb</code> without detail message.
     */
    public ErrorWeb() {
    }

    /**
     * Constructs an instance of <code>ErrorWeb</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public ErrorWeb(String msg) {
        super(msg);
    }
}
