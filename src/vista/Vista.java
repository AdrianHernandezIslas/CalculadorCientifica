/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

/**
 *
 * @author JULIO
 */
public class Vista extends JFrame{
    private JTextField display;
    private final String nombreBtn[] = {"(",")","1/x","x^2","x^3","x^n","√","n√x","sen","cos","tan","INV","RAD","π"};
    private JButton btnsEspeciales[];
    private final String nombreBtnEA[] = {"mc","m+","m-","mr","C","÷","x","<-"};
    private final String nombreBtnED[] = {"-","+","="};
    private Controlador controlador;
    private JPanel panelNumeros,panelBtnsEspeciales,panelDisplay,panelBtnsEA,panelBtnsED,panelOeste;
    private boolean esBasica,sonInversos;

    public Vista(){
        super("Calculadora");
        //                largo   ancho    de la ventana al comenzar en basica
        setBounds(550,250, 202  ,  300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout());
        setResizable(false);
        inicializarComponentes();
        
        generarBtnPrincipales();
        generarBtnEspeciales();
        generarBtnsEA();
        generarBtnsED();
        esBasica = true;
        sonInversos = false;
        setVisible(true);
        
    }
    
    
    private void inicializarComponentes(){
        controlador = new Controlador(this);
        btnsEspeciales = new JButton[nombreBtn.length];
        
        display = new JTextField();
        display.setColumns(7);
        display.setEditable(false);
        display.setBorder(new LineBorder(Color.WHITE));
        display.setFont(new Font("Calibri", 3, 20));
        
        panelOeste = new JPanel(new BorderLayout());
        
        
        panelNumeros = new JPanel(new GridLayout(4,3));
        panelBtnsEspeciales = new JPanel(new GridLayout(0,3));
        panelDisplay = new JPanel(new FlowLayout());
        
        JButton btn = new JButton("B/C");
        btn.addActionListener(controlador);
        
        panelDisplay.add(display);
        panelDisplay.add(btn);
       
        panelBtnsEA = new JPanel(new GridLayout(2,0));
        panelBtnsED = new JPanel(new GridLayout(0,1));
        
        panelOeste.add(panelBtnsEA,BorderLayout.NORTH);
        panelOeste.add(panelNumeros,BorderLayout.CENTER);
        panelOeste.add(panelBtnsED,BorderLayout.EAST);
        
        add(panelDisplay,BorderLayout.NORTH);
        add(panelOeste,BorderLayout.EAST);
        //add(panelBtnsEspeciales,BorderLayout.WEST);
        
    }
    
    private void generarBtnPrincipales(){
        JButton btn = null;
        for (int i = 9; i >= 0; i--) {
            btn = new JButton(String.valueOf(i));
            btn.addActionListener(controlador);
            btn.setBackground(new Color(251,251,251));
            panelNumeros.add(btn);
        }
        
        JButton btnPorcentaje = new JButton("%");
        btnPorcentaje.addActionListener(controlador);
        btnPorcentaje.setBackground(new Color(251,251,251));
        
        JButton btnPunto = new JButton(".");
        btnPunto.addActionListener(controlador);
        btnPunto.setBackground(new Color(251,251,251));
        
        panelNumeros.add(btnPorcentaje);
        panelNumeros.add(btnPunto);
    }
    
    private void generarBtnEspeciales(){
        JButton btn = null;
        for (int i = 0 ; i < nombreBtn.length ; i++) {
            btn = new JButton(String.valueOf(nombreBtn[i]));
            btn.addActionListener(controlador);
            btn.setBackground(new Color(214,214,214));
            btnsEspeciales[i] = btn;
            panelBtnsEspeciales.add(btn);
        }
        
       
    }
    
    private void generarBtnsEA(){
        JButton btn = null;
        for (int i = 0; i < nombreBtnEA.length ; i++) {
            btn = new JButton(nombreBtnEA[i]);
            btn.addActionListener(controlador);
            btn.setBackground(new Color(241,241,241));
            if(i > 3){
                btn.setForeground(new Color(62,142,245));
            }
            panelBtnsEA.add(btn);
        }
    }
    
    private void generarBtnsED(){
        JButton btn = null;
        for (int i = 0; i < nombreBtnED.length ; i++) {
            btn = new JButton(nombreBtnED[i]);
            btn.addActionListener(controlador);
            if(i == nombreBtnED.length -1){
                btn.setBackground(new Color(62,142,245));
                btn.setForeground(Color.WHITE);
            }else{
                btn.setBackground(new Color(241,241,241));
                btn.setForeground(new Color(62,142,245));
            }
            panelBtnsED.add(btn);
        }
    }

    public JTextField getDisplay() {
        return display;
    }

    public String[] getNombreBtn() {
        return nombreBtn;
    }
    
    public JPanel getPanel(){
        return panelBtnsEspeciales;
    }
    
    //Este metodo es donde tienes que ajustar el tamanio
    public void cambiarModalidad(){
        if(!esBasica){
            remove(panelBtnsEspeciales);
            //      largo   ancho
            setSize(202,300);//aqui ajustas el tamanio de la ventana para la basica 
            display.setColumns(7);//aqui el tamanio del display
            esBasica = !esBasica;
            if(sonInversos){
                btnsEspeciales[8].setText("sen");
                btnsEspeciales[9].setText("cos");
                btnsEspeciales[10].setText("tan");
                sonInversos = !sonInversos;
            }
        }else{
            add(panelBtnsEspeciales,BorderLayout.WEST);
            setSize(382,300);//aqui ajustas el tamanio de la ventana para cientifica
            display.setColumns(18);//Aqui ajustas el tamanio para el display
            esBasica = !esBasica;
        }
         
        SwingUtilities.updateComponentTreeUI(this);
    }
    
    public void cambiarAFuncionesInversas(){
        if (!sonInversos) {
            btnsEspeciales[8].setText("sen^-1");
            btnsEspeciales[9].setText("cos^-1");
            btnsEspeciales[10].setText("tan^-1");
            sonInversos = !sonInversos;
            setSize(426,300);
        }
    }
    
    
    
    public static void main(String[] args) {
        new Vista();
    }
}
