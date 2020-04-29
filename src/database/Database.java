/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showInputDialog;

/**
 *
 * @author Alex
 */
public class Database {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    Scanner teclado = new Scanner (System.in);
    boolean flag = false;
    String nome, tipo, fich;
    double telefono;
    int perso;
    int contador, cod_cli, cod_esc;
    ArrayList <Integer> codcliente = new ArrayList<Integer>();
    int seleccion = 99;
    ResultSet rs;
    
        try
        {
            
            String user=JOptionPane.showInputDialog(null, "Introduza usuario base de datos");
            String pass=JOptionPane.showInputDialog(null, "Introduza contrasinal");

            // carga do driver de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // conecto a miña base de datos
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",user,pass );
            Statement stm=null;
            System.out.println("Conectado á base de datos");


            while(!flag){
                switch (Menu()){
                    case 1:
                        System.out.println("\nIntroducir datos de novo cliente\n"
                                + "----------------------------------\n\n");
                        try{
                            System.out.print("Introduza nome: ");
                            nome = teclado.nextLine();
                            System.out.print("Introduza teléfono: ");
                            telefono = Double.parseDouble(teclado.nextLine());
                            stm=con.createStatement();
                            rs=stm.executeQuery("SELECT COD_CLIENTE FROM CLIENTES");
                            while(rs.next()){
                                codcliente.add(rs.getInt("COD_CLIENTE"));
                            }
                            contador =1;
                            while (codcliente.contains(contador)){
                                contador ++;
                            }
                            stm.executeUpdate("INSERT INTO CLIENTES ("
                                    + "COD_CLIENTE,NOMBRE,TELEFONO)"
                                    + "VALUES ("
                                    + contador+",'"+nome+"',"+telefono+")");
                        }catch(Exception ex){
                            System.out.println("Datos introducidos erróneos:\n"
                                    + ""+ex.getMessage());        
                        }
                        codcliente.clear();
                        JOptionPane.showMessageDialog(null, "Cliente creado","Cliente creado correctamente",JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 2:
                        System.out.println("\nIntroducir datos de nova escritura\n"
                                + "----------------------------------\n\n");
                        try{
                            System.out.print("Introduza tipo de documento (4 caracteres): ");
                            tipo = teclado.nextLine();
                            System.out.print("Introduza nome do ficheiro: ");
                            fich = teclado.nextLine();
                            System.out.print("Introduza persoas involucradas: ");
                            perso = Integer.parseInt(teclado.nextLine());
                            stm=con.createStatement();
                            rs=stm.executeQuery("SELECT CODIGO FROM ESCRITURAS");
                            while(rs.next()){
                                codcliente.add(rs.getInt("CODIGO"));
                            }
                            contador =1;
                            while (codcliente.contains(contador)){
                                contador ++;
                            }
                            stm.executeUpdate("INSERT INTO ESCRITURAS "
                                    + "VALUES ("
                                    + contador+",'"+tipo+"','"+fich+"',"+perso+")");
                        }catch(Exception ex){
                            System.out.println("Datos introducidos erróneos:\n"
                                    + ""+ex.getMessage());        
                        }
                        codcliente.clear();
                        JOptionPane.showMessageDialog(null, "Escritura creada","Escritura creada correctamente",JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 4:
                        stm=con.createStatement();
                        rs=stm.executeQuery("SELECT * FROM CLIENTES");
                        System.out.println("\nDatos de Clientes:\n"
                                + "--------------------------------------------\n");
                        while(rs.next()){
                            System.out.println("Código: "+rs.getString(1)+" Nome: "+rs.getString(2)+" Teléfono: "+rs.getString(3));
                        }
                        System.out.println("\n");
                        break;
                    case 5:
                        System.out.println("\nModificar cliente\n"
                                + "----------------------------------\n\n");
                        try{
                            System.out.print("Introduza código: ");
                            cod_cli = Integer.parseInt(teclado.nextLine());
                            System.out.print("Introduza nome: ");
                            nome = teclado.nextLine();
                            System.out.print("Introduza teléfono: ");
                            telefono = Double.parseDouble(teclado.nextLine());
                            stm=con.createStatement();
                            stm.executeUpdate("UPDATE CLIENTES SET NOMBRE = '"+nome+"' "
                                    + ",TELEFONO ="+telefono+" WHERE COD_CLIENTE = "+cod_cli+"");
                            
                        } catch (Exception ex){
                            System.out.println("Datos introducidos erróneos:\n"
                                    + ""+ex.getMessage());
                        }
                        JOptionPane.showMessageDialog(null, "Cliente modificado","Cliente modificado correctamente",JOptionPane.INFORMATION_MESSAGE);   
                        break;
                    case 6:
                        stm=con.createStatement();
                        rs=stm.executeQuery("SELECT * FROM ESCRITURAS");
                        System.out.println("\nDatos de Escrituras:\n"
                                + "--------------------------------------------\n");
                        while(rs.next()){
                            System.out.println("Código: "+rs.getString(1)+" Tipo: "+rs.getString(2)+" Ficheiro: "+rs.getString(3)+" Interveñen: "+rs.getString(4));
                        }
                        System.out.println("\n");
                        break; 
                    case 7:
                        System.out.println("\nModificar escritura\n"
                                + "----------------------------------\n\n");
                        try{
                            System.out.print("Introduza código: ");
                            cod_esc = Integer.parseInt(teclado.nextLine());
                            System.out.print("Introduza tipo de documento (4 caracteres): ");
                            tipo = teclado.nextLine();
                            System.out.print("Introduza nome do ficheiro: ");
                            fich = teclado.nextLine();
                            System.out.print("Introduza persoas involucradas: ");
                            perso = Integer.parseInt(teclado.nextLine());
                            stm=con.createStatement();
                            stm.executeUpdate("UPDATE ESCRITURAS SET TIPO = '"+tipo+"' "
                                    + ",NOM_FICH ='"+fich+"' ,NUM_INTERV = "+perso+" WHERE CODIGO = "+cod_esc+"");
                            
                        } catch (Exception ex){
                            System.out.println("Datos introducidos erróneos:\n"
                                    + ""+ex.getMessage());
                        }
                        JOptionPane.showMessageDialog(null, "Escritura modificada","Escritura modificada correctamente",JOptionPane.INFORMATION_MESSAGE);      
                        break;    
                        
                    case 3:  
                        System.out.println("\nVincular rexistros\n"
                                + "----------------------------------\n\n");

                        try{
                            System.out.print("Introduza código de cliente: ");
                            cod_cli = Integer.parseInt(teclado.nextLine());
                            System.out.print("Introduza código de escritura: ");
                            cod_esc = Integer.parseInt(teclado.nextLine());
                            stm=con.createStatement();
                            // búsqueda en clientes para verificar que existe
                            rs=stm.executeQuery("SELECT COD_CLIENTE FROM CLIENTES");
                            while(rs.next()){
                                codcliente.add(rs.getInt("COD_CLIENTE"));
                            }
                            if (!codcliente.contains(cod_cli)){
                                throw new Exception("Código cliente non existe");
                            }
                            codcliente.clear();
                            
                            //búsqueda en escrituras para verificar que existe
                            rs=stm.executeQuery("SELECT CODIGO FROM ESCRITURAS");
                            while(rs.next()){
                                codcliente.add(rs.getInt("CODIGO"));
                            }
                            if (!codcliente.contains(cod_esc)){
                                throw new Exception("Código escritura non existe");
                            }
                            codcliente.clear();
                            
                            // Introduzo vínculo
                            
                            stm.executeUpdate("INSERT INTO ESCCLI "
                                    + "VALUES ("
                                    + cod_cli+","+cod_esc+")");
                            JOptionPane.showMessageDialog(null, "Vínculo creado correctamente","Vínculo creado",JOptionPane.INFORMATION_MESSAGE);

                            
                        }catch (Exception ex){
                            System.out.println("Datos introducidos erróneos:\n"
                                    + ""+ex.getMessage());
                        }    
                            
                            
                            
                            
                            
                        codcliente.clear();    
                        break;
                    case 8:
                        System.out.println("\nClientes con escrituras\n"
                                + "----------------------------------\n\n");
                        stm=con.createStatement();
                        rs=stm.executeQuery("SELECT CLIENTES.NOMBRE FROM CLIENTES, ESCRITURAS, ESCCLI WHERE ("
                                + "CLIENTES.COD_CLIENTE = ESCCLI.COD_CLIENTE AND "
                                + "ESCRITURAS.CODIGO = ESCCLI.CODIGO AND "
                                + "ESCRITURAS.TIPO IS NOT NULL)");
                        while(rs.next()){
                            System.out.println("Nome: "+rs.getString(1));
                        }
                        System.out.println("\n");
                        break;    
                    case 9:
                        System.out.println("Saíndo do programa");
                        con.close();
                        flag = true;
                        break;                     
                }
            }
            
            
        }
        catch(Exception ex)
        {
            if (ex.getMessage().contains("ORA-01017")){
                JOptionPane.showMessageDialog(null, "Usuario de base de datos erróneo");
            } else{
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
        

    }        
    
    public static int Menu(){
        String retorno;
        int valor = 0;
        while (valor <1 || valor >9){
            retorno = JOptionPane.showInputDialog(null,"Selecciona unha opción:\n\n"
                    + "1- Dar de alta clientes\n"
                    + "2- Crear escrituras\n"
                    + "3- Vincular cliente e escritura\n"                    
                    + "4- Consultar clientes\n"
                    + "5- Modificar clientes\n"                    
                    + "6- Consultar escrituras\n"
                    + "7- Modificar escrituras\n"      
                    + "8- Listar os clientes con escrituras\n"
                    + "9- Sair \n\n");
            try{
                valor = Integer.parseInt(retorno);
            }catch (Exception ex){
                System.out.println("Selección non válida");
            }
        }
        return valor;
        
    }
}
