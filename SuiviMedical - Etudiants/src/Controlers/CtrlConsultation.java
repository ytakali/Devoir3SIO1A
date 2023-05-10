package Controlers;

import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CtrlConsultation
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlConsultation() {
        cnx = ConnexionBDD.getCnx();
    }

    public int getLastNumberOfConsultation()
    {
        int maxNumero = 0;

        // A vous de jouer
        try {
            ps = cnx.prepareStatement("SELECT consultation.idConsult " +
                    "FROM consultation " +
                    "ORDER BY consultation.idConsult " +
                    "DESC LIMIT 1;");
            rs = ps.executeQuery();
            rs.next();
            maxNumero = rs.getInt(1);
        }
        catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        return maxNumero;
    }
    public void InsertConsultation(int idConsult, int numPatient, int numMedecin, int numMed)
    {
        // A vous de jouer
        try {
            ps = cnx.prepareStatement("insert into consultation VALUES(?,?,?,?)");
            ps.setInt(1, idConsult);
            ps.setInt(2, numPatient);
            ps.setInt(3, numMedecin);
            ps.setInt(4, numMed);
            ps.executeUpdate();
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
