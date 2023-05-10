package Vues;

import Controlers.*;
import Tools.ConnexionBDD;
import Tools.ModelJTable;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.event.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;


public class FrmPrescrire extends JFrame
{
    private JPanel pnlRoot;
    private JLabel lblTitre;
    private JLabel lblNumero;
    private JLabel lblDate;
    private JLabel lblNomMedecin;
    private JTextField txtNumeroConsultation;
    private JComboBox cboPatients;
    private JComboBox cboMedecins;
    private JButton btnInserer;
    private JTable tblMedicaments;
    private JPanel pnlDate;
    private JLabel lblNomPatient;
    private JLabel lblMedicaments;
    private JDateChooser dcDateConsultation;

    CtrlConsultation ctrlConsultation;
    CtrlMedecin ctrlMedecin;
    CtrlPatient ctrlPatient;
    CtrlMedicament ctrlMedicament;
    CtrlPrescrire ctrlPrescrire;
    ModelJTable mdl;
    public FrmPrescrire() throws SQLException, ClassNotFoundException {
        this.setTitle("Prescrire");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        ConnexionBDD maCnx = new ConnexionBDD();

        // Gestion de la date : ne pas supprimer les 3 lignes de code
        dcDateConsultation = new JDateChooser();
        dcDateConsultation.setDateFormatString("yyyy-MM-dd");
        pnlDate.add(dcDateConsultation);

        // A vous de jouer
        ctrlConsultation = new CtrlConsultation();
        ctrlMedecin = new CtrlMedecin();
        ctrlPatient = new CtrlPatient();
        ctrlMedicament = new CtrlMedicament();
        ctrlPrescrire = new CtrlPrescrire();

        AfficheMed();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);

                mdl = new ModelJTable();
                mdl.loadDatasMedicaments(ctrlMedicament.getAllMedicaments());
                tblMedicaments.setModel(mdl);

            }
        });

        btnInserer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // A vous de jouer
                int idConsult = ctrlConsultation.getLastNumberOfConsultation();
                String dateConsult = "yyyy-mm-jj";
                dcDateConsultation.setDateFormatString("yyyy-mm-jj");
                int numPat =ctrlPatient.getIdPatientByName(cboPatients.getSelectedItem().toString());
                int numMed =ctrlMedecin.getIdMedecinByName(cboMedecins.getSelectedItem().toString());
                ctrlConsultation.InsertConsultation(idConsult, Integer.parseInt(dateConsult),numPat,numMed);

                for (int i =0 ; i<tblMedicaments.getRowCount(); i++) {
                    String numpiz = tblMedicaments.getValueAt(i, 0).toString();
                    int qte = Integer.parseInt(tblMedicaments.getValueAt(i,4).toString());
                    if (qte != 0) {
                        ctrlPrescrire.InsertPrescrire(idConsult, Integer.parseInt(dateConsult),numPat, numMed);
                    }
                }

            }
        });
    }

    public void AfficheMed()
    {
        txtNumeroConsultation.setText(String.valueOf(ctrlConsultation.getLastNumberOfConsultation()+1));
        for (String patient : ctrlPatient.getAllPatients())
        {
            cboPatients.addItem(patient);
        }

        for (String medecin : ctrlMedecin.getAllMedecins())
        {
            cboMedecins.addItem(medecin);
        }

        mdl = new ModelJTable();
        mdl.loadDatasMedicaments(ctrlMedicament.getAllMedicaments());
        tblMedicaments.setModel(mdl);
    }
}
