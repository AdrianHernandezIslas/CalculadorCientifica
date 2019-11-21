/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JButton;
import vista.Vista;

/**
 *
 * @author JULIO
 */
public class Controlador implements ActionListener{
    private final Vista vista;
    private String expresion;
    private boolean puedesPonerPunto,banderaRaizN,banderaExpN;

    public Controlador(Vista vista) {
        
        this.vista = vista;
        expresion = "";
        puedesPonerPunto = true;
        banderaRaizN = false;
        banderaExpN = false;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JButton btn = (JButton) event.getSource();
        
        if(esNumero(btn.getText())){
            vista.getDisplay().setText(vista.getDisplay().getText()+btn.getText());
            expresion += btn.getText();
        }
        
        if (esOperadorAritmetico(btn.getText())) {
            if(btn.getText().equals("÷")){
                expresion += "/";
            }else if(btn.getText().equals("x")){
               expresion += "*";
            }else{
                expresion += btn.getText();
            }
            vista.getDisplay().setText(vista.getDisplay().getText()+btn.getText());
            
            puedesPonerPunto = true;
        }
        
        if(btn.getText().equals("%")){
            porcentaje();
            vista.getDisplay().setText(vista.getDisplay().getText()+btn.getText());
        }
        
        if(btn.getText().equals("C")){
            vista.getDisplay().setText("");
            expresion="";
        }
        
        if(btn.getText().equals("<-")){
            String operacion = vista.getDisplay().getText();
            System.out.println(operacion.length());
            if (operacion.length() > 0) {
                vista.getDisplay().setText(operacion.substring(0,operacion.length()-1));
                expresion = expresion.substring(0,expresion.length()-1);
                if (vista.getDisplay().getText().length() == 0) {
                    expresion = "";
                }
            }
        }
        
        accionesTeclasEspeciales(btn.getText());
        
        if(btn.getText().equals("=")){
            String resultado = String.valueOf(evaluar(expresion));
            expresion = resultado;
            vista.getDisplay().setText(resultado) ;
        }
        
        if(btn.getText().equals("B/C")){
           vista.cambiarModalidad();
        }
    }
    
    private boolean esNumero(String valor){
        try {
            Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private void accionesTeclasEspeciales(String valor){
        
        if(banderaRaizN){
            raizN();
            banderaRaizN = false;
        }
        
        if(banderaExpN){
            exponenteN();
            banderaExpN = false;
        }
        
        if(valor.equals("(") || valor.equals(")")){
            vista.getDisplay().setText(vista.getDisplay().getText()+valor);
            expresion += valor;
            puedesPonerPunto = true;
        }
        
        if(valor.equals("cos")){
            vista.getDisplay().setText(vista.getDisplay().getText()+valor+"(");
            expresion +="Math.cos(";
            puedesPonerPunto = true;
        }
        
        if(valor.equals("sen")){
            vista.getDisplay().setText(vista.getDisplay().getText()+valor+"(");
            expresion +="Math.sin(";
            puedesPonerPunto = true;
        }
        
        if(valor.equals("tan")){
            vista.getDisplay().setText(vista.getDisplay().getText()+valor+"(");
            expresion +="Math.tan(";
            puedesPonerPunto = true;
        }
        
        if(valor.equals("π")){
            vista.getDisplay().setText(vista.getDisplay().getText()+valor);
            expresion +="3.1416";
            puedesPonerPunto = true;
        }
        
        if(valor.equals("√")){
            vista.getDisplay().setText(vista.getDisplay().getText()+valor);
            expresion +="Math.sqrt(";
            puedesPonerPunto = true;
        }
        
        if(valor.equals("x^2")){
            vista.getDisplay().setText(vista.getDisplay().getText()+"^2");
            exponente2();
            puedesPonerPunto = true;
        }
        
        if(valor.equals("x^3")){
            vista.getDisplay().setText(vista.getDisplay().getText()+"^3");
            exponente3();
            puedesPonerPunto = true;
        }
        
        if(valor.equals("x^n")){
            vista.getDisplay().setText(vista.getDisplay().getText()+"^");
            puedesPonerPunto = true;
            banderaExpN = true;
        }
        
        
        if (valor.equals("n√x")) {
            vista.getDisplay().setText(vista.getDisplay().getText()+"√");
            puedesPonerPunto = true;
            banderaRaizN = true;
            
        }
        
        if (valor.equals(".") && puedesPonerPunto) {
             vista.getDisplay().setText(vista.getDisplay().getText()+".");
             expresion += ".";  
             puedesPonerPunto = false;
        }
        
        if(valor.equals("INV")){
            //vista.cambiarAFuncionesInversas();
        }
        
        System.out.println(expresion);
       
    }
    
    private void raizN(){
        String textoExpesion = vista.getDisplay().getText();
        String radical = ""+textoExpesion.charAt(textoExpesion.length()-3);
        String radicando  = ""+textoExpesion.charAt(textoExpesion.length()-1);
        String expRadical = "Math.pow("+Double.parseDouble(radicando)+",1.0/"+Double.parseDouble(radical)+")";
        limpirarExpresion(textoExpesion.length()-3, textoExpesion.length()-1);
        expresion+=expRadical;
    }
    
    private void exponenteN() {
        String textoExpesion = vista.getDisplay().getText();
        if(textoExpesion.length() > 2){
            String base = ""+textoExpesion.charAt(textoExpesion.length()-3);
            String exponente  = ""+textoExpesion.charAt(textoExpesion.length()-1);
            String expRadical = "Math.pow("+Double.parseDouble(base)+","+Double.parseDouble(exponente)+")";
            limpirarExpresion(textoExpesion.length()-3, textoExpesion.length()-1);
            expresion+=expRadical;
        }
    }
    
    private void exponente2() {
        String textoExpesion = vista.getDisplay().getText();
        if(textoExpesion.length() > 2){
            String base = ""+textoExpesion.charAt(textoExpesion.length()-3);
            limpirarExpresion(textoExpesion.length()-3, -1);
            expresion += "Math.pow("+base+",2)";
        }  
    }
    
    private void exponente3() {
        String textoExpesion = vista.getDisplay().getText();
        if(textoExpesion.length() > 2){
            String base = ""+textoExpesion.charAt(textoExpesion.length()-3);
            limpirarExpresion(textoExpesion.length()-3, -1);
            expresion += "Math.pow("+base+",3)";
        }  
    }
    
    private void limpirarExpresion(int posicion1 , int posicion2){
        String nuevaExpresion = "";
        
        for (int i = 0; i < expresion.length() ; i++) {
            if(i != posicion1 && i != posicion2-1){
                nuevaExpresion += expresion.charAt(i);
            }
        }
        expresion = nuevaExpresion;
        
    }
    
    private boolean esOperadorAritmetico(String valor){
        return valor.equals("+") || valor.equals("-") || valor.equals("÷") || valor.equals("x");
    }
    
    private void porcentaje(){
        String valores [] = null;
        double porcentajeAQuitar = 0;
        double cantidad = 0;
        double porcentaje = 0 ;
        if(expresion.contains("+")){
            valores = expresion.split("\\+");
            cantidad = Double.parseDouble(valores[0]);
            porcentaje = Double.parseDouble(valores[1]);
            porcentajeAQuitar = cantidad*(porcentaje/100.0);
            cantidad += porcentajeAQuitar;
            
        }
        
        if(expresion.contains("-")){
            valores = expresion.split("\\-");
            cantidad = Double.parseDouble(valores[0]);
            porcentaje = Double.parseDouble(valores[1]);
            porcentajeAQuitar = cantidad*(porcentaje/100.0);
            cantidad -= porcentajeAQuitar;
        }
        expresion = String.valueOf(cantidad);
        
    }
    
    private double evaluar(String func){
        Object o = null;
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
              engine.eval("function myFunction(x){var output = '';"
                  + "{output = "+func+";"
                  + "  } return output;}");
              Invocable invokeEngine = (Invocable) engine;
              o = invokeEngine.invokeFunction("myFunction",1.0);
              if(o instanceof Integer){
                return (int)o;
              }else{
                return (double)o;
              }
        } catch (Exception e) {
                //System.err.println(e);
        }
        return 0.0;
        
    }
    
}
