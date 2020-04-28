/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
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
    boolean flag = false;
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


            while (!flag){
                switch (Menu()){
                    case 1:
                        stm=con.createStatement();
                        stm.executeUpdate("CREATE TABLE CLIENTES ("
                                + "COD_CLIENTE NUMBER(5) PRIMARY KEY,"
                                + "NOMBRE VARCHAR2(20),"
                                + "TELEFONO NUMBER (11))");
                        break;
                    case 2:
                        stm=con.createStatement();
                        stm.executeUpdate("CREATE TABLE ESCRITURAS ("
                                + "CODIGO VARCHAR2(5) PRIMARY KEY,"
                                + "TIPO VARCHAR2(4),"
                                + "NOM_FICH VARCHAR (40),"
                                + "NUM_INTERV NUMBER (2))");
                        break;
                    case 3:
                        stm=con.createStatement();
                        stm.executeUpdate("CREATE TABLE ESCCLI ("
                                + "COD_CLIENTE NUMBER(5) REFERENCES CLIENTES,"
                                + "CODIGO VARCHAR2(5) REFERENCES ESCRITURAS)");                   
                        break; 
                    case 4:
                        seleccion = JOptionPane.showConfirmDialog(null,"Engadir datos automáticamente (premer OK) ou cancelar", "Introducción de datos", JOptionPane.OK_CANCEL_OPTION);
                        if (seleccion == 0){
                            stm=con.createStatement();
                            stm.executeUpdate("INSERT INTO CLIENTES ("
                                    + "COD_CLIENTE,NOMBRE,TELEFONO)"
                                    + "VALUES ("
                                    + "0001,'Pedro',986254125)");
                            stm.executeUpdate("INSERT INTO CLIENTES ("
                                    + "COD_CLIENTE,NOMBRE,TELEFONO)"
                                    + "VALUES ("
                                    + "0002,'Paco',662254125)");
                            stm.executeUpdate("INSERT INTO CLIENTES ("
                                    + "COD_CLIENTE,NOMBRE,TELEFONO)"
                                    + "VALUES ("
                                    + "0003,'Raquel',null)");
                        }
                        break;
                    case 5:
                        seleccion = JOptionPane.showConfirmDialog(null,"Engadir datos automáticamente (premer OK) ou cancelar", "Introducción de datos", JOptionPane.OK_CANCEL_OPTION);
                        if (seleccion == 0){
                            stm=con.createStatement();
                            stm.executeUpdate("INSERT INTO ESCRITURAS "
                                    + "VALUES ("
                                    + "'A001C','TEST','TESTAMENTO_PACO_1.DOC',2)");
                            stm.executeUpdate("INSERT INTO ESCRITURAS "
                                    + "VALUES ("
                                    + "'B078F','TEST','TESTAMENTO_PEDRO_1.DOC',2)");
                            stm.executeUpdate("INSERT INTO ESCRITURAS "
                                    + "VALUES ("
                                    + "'GF7R2','CPVE','COMPRA_PISO_GF7R2_1.DOC',4)");
                            stm.executeUpdate("INSERT INTO ESCCLI "
                                    + "VALUES ("
                                    + "0001,'B078F')");
                            stm.executeUpdate("INSERT INTO ESCCLI "
                                    + "VALUES ("
                                    + "0002,'A001C')");
                            stm.executeUpdate("INSERT INTO ESCCLI "
                                    + "VALUES ("
                                    + "0003,'GF7R2')");
                        }
                        break;
                    case 6:
                        stm=con.createStatement();
                        rs=stm.executeQuery("SELECT * FROM CLIENTES");
                        System.out.println("\nDatos de Clientes:\n"
                                + "--------------------------------------------\n");
                        while(rs.next()){
                            System.out.println("Código: "+rs.getString("COD_CLIENTE")+" Nome: "+rs.getString("NOMBRE")+" Teléfono: "+rs.getString("TELEFONO"));
                        }
                        System.out.println("\n");
                        break;
                    case 7:
                        stm=con.createStatement();
                        rs=stm.executeQuery("SELECT * FROM ESCRITURAS");
                        System.out.println("\nDatos de Escrituras:\n"
                                + "--------------------------------------------\n");
                        while(rs.next()){
                            System.out.println("Código: "+rs.getString(1)+" Tipo: "+rs.getString(2)+" Ficheiro: "+rs.getString(3)+" Interveñen: "+rs.getString(4));
                        }
                        System.out.println("\n");
                        break;
                    case 8:
                        stm=con.createStatement();
                        stm.executeUpdate("UPDATE CLIENTES SET TELEFONO =675849875 WHERE COD_CLIENTE = 3");
                        break;
                    case 9:
                        stm=con.createStatement();
                        rs=stm.executeQuery("SELECT CLIENTES.NOMBRE FROM CLIENTES, ESCRITURAS, ESCCLI WHERE ("
                                + "CLIENTES.COD_CLIENTE = ESCCLI.COD_CLIENTE AND "
                                + "ESCRITURAS.CODIGO = ESCCLI.CODIGO AND "
                                + "ESCRITURAS.TIPO='CPVE')");
                        System.out.println("\nDatos de clientes con documento tipo CPVE:\n"
                                + "--------------------------------------------\n");
                        while(rs.next()){
                            System.out.println("Nome: "+rs.getString(1));
                        }
                        System.out.println("\n");
                        break;
                    case 10:
                        System.out.println("Saíndo do programa");
                        con.close();
                        flag = true;
                        break;
                }
            }
            
            
            /*---- 
            Statement stm=con.createStatement();
            ResultSet rs=stm.executeQuery("SELECT * FROM LINEAS");
            while(rs.next()){
                System.out.println(rs.getString("CANTIDAD"));
            }
            stm.executeUpdate("UPDATE EMPLEADOS SET NOMBRE ='Jose Luis Perez' WHERE CODIGO = 1");

            con.close();*/
        }
        catch(Exception ex)
        {
            if (ex.getMessage().contains("ORA-01017")){
                JOptionPane.showMessageDialog(null, "Usuario de base de datos erróneo");
            } else if (ex.getMessage().contains("ORA-00955")){
                JOptionPane.showMessageDialog(null, "Tabla ya existe en la base de datos");
            } else if (ex.getMessage().contains("ORA-00942")){
                JOptionPane.showMessageDialog(null, "Falta tabla de referencia en la base de datos");
            } else{
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
        

    }        
    
    public static int Menu(){
        String retorno;
        int valor = 0;
        while (valor <1 || valor >10){
            retorno = JOptionPane.showInputDialog(null,"Selecciona unha opción:\n\n"
                    + "1- Crear tabla Clientes\n"
                    + "2- Crear tabla Escrituras\n"
                    + "3- Crear tabla EscCli\n"
                    + "4- Insertar datos en Clientes\n"
                    + "5- Insertar datos en Escrituras y EscCli\n"
                    + "6- Recuperar datos de tabla Clientes\n"
                    + "7- Recuperar datos de tabla Escritura\n"
                    + "8- Actualizar tabla Clientes\n"
                    + "9- Listar nombre de clientes con tipo escritura CPVE \n"
                    + "10- Sair \n\n");
            try{
                valor = Integer.parseInt(retorno);
            }catch (Exception ex){
                System.out.println("Selección non válida");
            }
        }
        return valor;
        
    }
}
