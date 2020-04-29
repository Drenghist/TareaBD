/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.awt.HeadlessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex
 * @version 2.0
 * @date 29/04/2020
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
    ArrayList <Integer> codcliente = new ArrayList<>();
    ResultSet rs;
    
        try
        {
            String user=JOptionPane.showInputDialog(null, "Introduza usuario base de datos");
            String pass=JOptionPane.showInputDialog(null, "Introduza contrasinal");

            // carga do driver de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // conecto a miña base de datos
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",user,pass );
            Statement stm;
            JOptionPane.showMessageDialog(null, "Conectado á base de datos co usuario "+user,"Conectado",JOptionPane.INFORMATION_MESSAGE);


            while(!flag){
                switch (Menu()){
                    case 1: //Crear novo cliente
                        System.out.println("\nIntroducir datos de novo cliente\n"
                                + "----------------------------------\n\n");
                        try{
                            //Obteño os datos por teclado
                            System.out.print("Introduza nome (max 20 caracteres): ");
                            nome = teclado.nextLine();
                            System.out.print("Introduza teléfono (max 10 díxitos): ");
                            telefono = Double.parseDouble(teclado.nextLine());
                            
                            //Consulto os códigos que existen na BD e escollo o seguinte libre
                            codcliente.clear();
                            stm=con.createStatement();
                            rs=stm.executeQuery("SELECT COD_CLIENTE FROM CLIENTES");
                            while(rs.next()){
                                codcliente.add(rs.getInt("COD_CLIENTE"));
                            }
                            contador =1;
                            while (codcliente.contains(contador)){
                                contador ++;
                            }
                            
                            //Inserto os datos na BD
                            stm.executeUpdate("INSERT INTO CLIENTES ("
                                    + "COD_CLIENTE,NOMBRE,TELEFONO)"
                                    + "VALUES ("
                                    + contador+",'"+nome+"',"+telefono+")");
                            JOptionPane.showMessageDialog(null, "Cliente creado","Cliente creado correctamente",JOptionPane.INFORMATION_MESSAGE);
                            
                        }catch(NumberFormatException | SQLException ex){
                            System.out.println("Datos introducidos erróneos:\n"
                                    + ""+ex.getMessage());        
                        }
                        break;
                    case 2: //Crear nova escritura
                        System.out.println("\nIntroducir datos de nova escritura\n"
                                + "----------------------------------\n\n");
                        try{
                            //Obteño os datos por teclado
                            System.out.print("Introduza tipo de documento (max 4 caracteres): ");
                            tipo = teclado.nextLine();
                            System.out.print("Introduza nome do ficheiro (max 40 caracteres): ");
                            fich = teclado.nextLine();
                            System.out.print("Introduza persoas involucradas (max 2 díxitos: ");
                            perso = Integer.parseInt(teclado.nextLine());
                            
                            //Consulto os códigos que existen na BD e escollo o seguinte libre
                            codcliente.clear();
                            stm=con.createStatement();
                            rs=stm.executeQuery("SELECT CODIGO FROM ESCRITURAS");
                            while(rs.next()){
                                codcliente.add(rs.getInt("CODIGO"));
                            }
                            contador =1;
                            while (codcliente.contains(contador)){
                                contador ++;
                            }
                            
                            //Inserto os datos na BD
                            stm.executeUpdate("INSERT INTO ESCRITURAS "
                                    + "VALUES ("
                                    + contador+",'"+tipo+"','"+fich+"',"+perso+")");
                            JOptionPane.showMessageDialog(null, "Escritura creada","Escritura creada correctamente",JOptionPane.INFORMATION_MESSAGE);
                            
                        }catch(NumberFormatException | SQLException ex){
                            System.out.println("Datos introducidos erróneos:\n"
                                    + ""+ex.getMessage());        
                        }
                        break;
                        
                    case 4: //Mostro os datos de clientes por pantalla
                        stm=con.createStatement();
                        rs=stm.executeQuery("SELECT * FROM CLIENTES");
                        System.out.println("\nDatos de Clientes:\n"
                                + "--------------------------------------------\n");
                        while(rs.next()){
                            System.out.println("Código: "+rs.getString(1)+" Nome: "+rs.getString(2)+" Teléfono: "+rs.getString(3));
                        }
                        System.out.println("\n");
                        break;
                        
                    case 5: //Modifico o cliente
                        System.out.println("\nModificar cliente\n"
                                + "----------------------------------\n\n");
                        try{
                            //Solicito os datos por teclado
                            System.out.print("Introduza código: ");
                            cod_cli = Integer.parseInt(teclado.nextLine());
                            System.out.print("Introduza nome (max 20 caracteres): ");
                            nome = teclado.nextLine();
                            System.out.print("Introduza teléfono (max 10 díxitos): ");
                            telefono = Double.parseDouble(teclado.nextLine());
                            
                            // búsqueda en clientes para verificar que existe
                            codcliente.clear();
                            stm=con.createStatement();
                            rs=stm.executeQuery("SELECT COD_CLIENTE FROM CLIENTES");
                            while(rs.next()){
                                codcliente.add(rs.getInt("COD_CLIENTE"));
                            }
                            if (!codcliente.contains(cod_cli)){
                                throw new Exception("Código cliente non existe");
                            }
                            
                            //Actualizo a BD
                            stm.executeUpdate("UPDATE CLIENTES SET NOMBRE = '"+nome+"' "
                                    + ",TELEFONO ="+telefono+" WHERE COD_CLIENTE = "+cod_cli+"");
                            JOptionPane.showMessageDialog(null, "Cliente modificado","Cliente modificado correctamente",JOptionPane.INFORMATION_MESSAGE); 
                            
                        } catch (Exception ex){
                            System.out.println("Datos introducidos erróneos:\n"
                                    + ""+ex.getMessage());
                        }
                        break;
                        
                    case 6: //Mostro os datos de clientes por pantalla
                        stm=con.createStatement();
                        rs=stm.executeQuery("SELECT * FROM ESCRITURAS");
                        System.out.println("\nDatos de Escrituras:\n"
                                + "--------------------------------------------\n");
                        while(rs.next()){
                            System.out.println("Código: "+rs.getString(1)+" Tipo: "+rs.getString(2)+" Ficheiro: "+rs.getString(3)+" Interveñen: "+rs.getString(4));
                        }
                        System.out.println("\n");
                        break; 
                    case 7: //Modifico a escritura
                        System.out.println("\nModificar escritura\n"
                                + "----------------------------------\n\n");
                        try{
                            //Solicito os datos por teclado
                            System.out.print("Introduza código: ");
                            cod_esc = Integer.parseInt(teclado.nextLine());
                            System.out.print("Introduza tipo de documento (max 4 caracteres): ");
                            tipo = teclado.nextLine();
                            System.out.print("Introduza nome do ficheiro (max 40 caracteres): ");
                            fich = teclado.nextLine();
                            System.out.print("Introduza persoas involucradas (max 2 díxitos): ");
                            perso = Integer.parseInt(teclado.nextLine());
                            
                            //búsqueda en escrituras para verificar que existe
                            codcliente.clear();
                            stm=con.createStatement();
                            rs=stm.executeQuery("SELECT CODIGO FROM ESCRITURAS");
                            while(rs.next()){
                                codcliente.add(rs.getInt("CODIGO"));
                            }
                            if (!codcliente.contains(cod_esc)){
                                throw new Exception("Código escritura non existe");
                            }
                            
                            //Actualizo a BD
                            stm.executeUpdate("UPDATE ESCRITURAS SET TIPO = '"+tipo+"' "
                                    + ",NOM_FICH ='"+fich+"' ,NUM_INTERV = "+perso+" WHERE CODIGO = "+cod_esc+"");
                            JOptionPane.showMessageDialog(null, "Escritura modificada","Escritura modificada correctamente",JOptionPane.INFORMATION_MESSAGE); 
                            
                        } catch (Exception ex){
                            System.out.println("Datos introducidos erróneos:\n"
                                    + ""+ex.getMessage());
                        }
                        break;    
                        
                    case 3:  //Vínculo de clientes con escrituras
                        System.out.println("\nVincular rexistros\n"
                                + "----------------------------------\n\n");

                        try{
                            //Solicito datos por teclado
                            System.out.print("Introduza código de cliente: ");
                            cod_cli = Integer.parseInt(teclado.nextLine());
                            System.out.print("Introduza código de escritura: ");
                            cod_esc = Integer.parseInt(teclado.nextLine());
                            stm=con.createStatement();
                            
                            // búsqueda en clientes para verificar que existe
                            codcliente.clear();
                            rs=stm.executeQuery("SELECT COD_CLIENTE FROM CLIENTES");
                            while(rs.next()){
                                codcliente.add(rs.getInt("COD_CLIENTE"));
                            }
                            if (!codcliente.contains(cod_cli)){
                                throw new Exception("Código cliente non existe");
                            }
                            
                            //búsqueda en escrituras para verificar que existe
                            codcliente.clear();
                            rs=stm.executeQuery("SELECT CODIGO FROM ESCRITURAS");
                            while(rs.next()){
                                codcliente.add(rs.getInt("CODIGO"));
                            }
                            if (!codcliente.contains(cod_esc)){
                                throw new Exception("Código escritura non existe");
                            }
                            
                            // Introduzo vínculo en BD
                            stm.executeUpdate("INSERT INTO ESCCLI "
                                    + "VALUES ("
                                    + cod_cli+","+cod_esc+")");
                            JOptionPane.showMessageDialog(null, "Vínculo creado correctamente","Vínculo creado",JOptionPane.INFORMATION_MESSAGE);
                            
                        }catch (Exception ex){
                            System.out.println("Datos introducidos erróneos:\n"
                                    + ""+ex.getMessage());
                        }    
                        break;
                        
                    case 8: //Mostra os clientes con escrituras
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
                        
                    case 9: //Sae do programa
                        System.out.println("Saíndo do programa");
                        con.close();
                        flag = true;
                        break;                     
                }
            }
            
            
        }
        catch(HeadlessException | ClassNotFoundException | SQLException ex)
        {
            if (ex.getMessage().contains("ORA-01017")){
                JOptionPane.showMessageDialog(null, "Usuario de base de datos erróneo");
            } else{
                System.out.println(ex.getMessage());
            }
        }
        

    }        
    
    /**
     *
     * @return opción seleccionada
     */
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
            }catch (NumberFormatException ex){
                System.out.println("Selección non válida");
            }
        }
        return valor;
        
    }
}
