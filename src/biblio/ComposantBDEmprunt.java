package biblio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
/**
 * Composant logiciel assurant la gestion des emprunts d'exemplaires
 * de livre par les abonnés.
 */
public class ComposantBDEmprunt {

  /**
   * Retourne le nombre total d'emprunts en cours référencés dans la base.
   * 
   * @return le nombre d'emprunts.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int nbEmpruntsEnCours() throws SQLException {
    //
    // COMPLET
    //
    ArrayList<String[]> emprunts = ComposantBDEmprunt.listeEmpruntsEnCours();
	return emprunts.size();
  }

  /**
   * Retourne le nombre d'emprunts en cours pour un abonné donné connu
   * par son identifiant.
   * 
   * @return le nombre d'emprunts.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int nbEmpruntsEnCours(int idAbonne) throws SQLException {
    //
    // COMPLET
    //
	ArrayList<String[]> emprunts = ComposantBDEmprunt.listeEmpruntsEnCours(idAbonne);
    return emprunts.size();
  }

  
  /**
   * Récupération de la liste complète des emprunts en cours.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt en cours.<br/>
   * Il doit contenir 8 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : id de l'abonné</li>
   *   <li>5 : nom de l'abonné</li>
   *   <li>6 : son prénom</li>
   *   <li>7 : la date de l'emprunt</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeEmpruntsEnCours() throws SQLException {
    ArrayList<String[]> emprunts = new ArrayList<String[]>();
    //
    // COMPLET
    //
    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select * from emprunt where dateretour is null";
    ResultSet rset = stmt.executeQuery(sql);
    while (rset.next()){
    	String[] emprunt = new String[8];
   	    emprunt[0] = rset.getString("ide");
    	String[] livre = ComposantBDLivre.getLivreParIdExemplaire(Integer.valueOf(emprunt[0]));
    	String idAbonne = rset.getString("ida"); 
    	String[] abonne = ComposantBDAbonne.getAbonne(Integer.valueOf(idAbonne)); 
   	    emprunt[1] = livre[0];
   	    emprunt[2] = livre[3];
   	    emprunt[3] = livre[4];
   	    emprunt[4] = abonne[0];
   	    emprunt[5] = abonne[1];
   	    emprunt[6] = abonne[2]; 
   	    emprunt[7] = rset.getString("dateemprunt");
   	    emprunts.add(emprunt);
    }
    return emprunts;
  }

  /**
   * Récupération de la liste des emprunts en cours pour un abonné donné.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt en cours pour l'abonné.<br/>
   * Il doit contenir 5 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : la date de l'emprunt</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeEmpruntsEnCours(int idAbonne) throws SQLException {
    ArrayList<String[]> emprunts = new ArrayList<String[]>();
    //
    // COMPLET
    //
    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select * from emprunt where ida="+idAbonne+" and dateretour is null";
    ResultSet rset = stmt.executeQuery(sql);
    while (rset.next()){
        
    	String[] emprunt = new String[5];
   	    emprunt[0] = rset.getString("ide"); 
   	    String[] livre = ComposantBDLivre.getLivreParIdExemplaire(Integer.valueOf(emprunt[0]));
   	    emprunt[1] = livre[0];
   	    emprunt[2] = livre[3];
   	    emprunt[3] = livre[4];
   	    emprunt[4] = rset.getString("dateemprunt");
   	    emprunts.add(emprunt);
    }
    rset.close();
    stmt.close();
    return emprunts;
  }

  /**
   * Récupération de la liste complète des emprunts passés.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt passé.<br/>
   * Il doit contenir 9 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : id de l'abonné</li>
   *   <li>5 : nom de l'abonné</li>
   *   <li>6 : son prénom</li>
   *   <li>7 : la date de l'emprunt</li>
   *   <li>8 : la date de retour</li>
   * </ul>
   * @return un <code>ArrayList</code> contenant autant de tableaux de String (5 chaînes de caractères) que d'emprunts dans la base.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeEmpruntsHistorique() throws SQLException {
    ArrayList<String[]> emprunts = new ArrayList<String[]>();
    //
    // COMPLET
    //
    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select * from emprunt where dateretour is not null";
    ResultSet rset = stmt.executeQuery(sql);
    while (rset.next()){
    	String[] emprunt = new String[9];
   	    emprunt[0] = rset.getString("ide");
    	String[] livre = ComposantBDLivre.getLivreParIdExemplaire(Integer.valueOf(emprunt[0]));
    	String idAbonne = rset.getString("ida"); 
    	String[] abonne = ComposantBDAbonne.getAbonne(Integer.valueOf(idAbonne)); 
   	    emprunt[1] = livre[0];
   	    emprunt[2] = livre[3];
   	    emprunt[3] = livre[4];
   	    emprunt[4] = abonne[0];
   	    emprunt[5] = abonne[1];
   	    emprunt[6] = abonne[2]; 
   	    emprunt[7] = rset.getString("dateemprunt");
   	    emprunt[8] = rset.getString("dateretour");
   	    emprunts.add(emprunt);
    }
    rset.close();
    stmt.close();
    return emprunts;
  }

  /**
   * Emprunter un exemplaire à partir de l'identifiant de l'abonné et de
   * l'identifiant de l'exemplaire.
   * 
   * @param idAbonne : id de l'abonné emprunteur.
   * @param idExemplaire id de l'exemplaire emprunté.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void emprunter(int idAbonne, int idExemplaire) throws SQLException {
    // 
    // COMPLET
    //
		if (ComposantBDEmprunt.estEmprunte(idExemplaire) == false){
		Statement stmt = Connexion.getConnection().createStatement();
		String formatDate = "dd/MM/yy hh:mm:ss";
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(formatDate);
		Calendar calendar = Calendar.getInstance();
		String dateEmprunt = formater.format(calendar.getTime());
		String sql = "insert into emprunt values(nextval('emprunt_idemprunt_seq'),'"+dateEmprunt+"',"+null+","+idAbonne+","+idExemplaire+")";
		int i = stmt.executeUpdate(sql);
		stmt.close();
	}

		    
  }

  /**
   * Retourner un exemplaire à partir de son identifiant.
   * 
   * @param idExemplaire id de l'exemplaire à rendre.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void rendre(int idExemplaire) throws SQLException {
    // 
    // COMPLET
    //
	if (ComposantBDEmprunt.estEmprunte(idExemplaire) == true){
	  Statement stmt = Connexion.getConnection().createStatement();
      String formatDate = "dd/MM/yy hh:mm:ss";
	  java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(formatDate);
	  Calendar calendar = Calendar.getInstance();
	  String dateRetour = formater.format(calendar.getTime());
	  String sql = "update emprunt set dateretour='"+dateRetour+"' where ide="+idExemplaire;
	  int i = stmt.executeUpdate(sql);
	  stmt.close();	
	}
  }
  
  /**
   * Détermine si un exemplaire sonné connu par son identifiant est
   * actuellement emprunté.
   * 
   * @param idExemplaire
   * @return <code>true</code> si l'exemplaire est emprunté, <code>false</code> sinon
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static boolean estEmprunte(int idExemplaire) throws SQLException {
    boolean estEmprunte = false;
    //
    // COMPLET
    //
    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select * from emprunt where ide="+idExemplaire;
    ResultSet rset = stmt.executeQuery(sql);
    while (rset.next()){
    	if(rset.getString("dateretour") == null)
    		estEmprunte = true;
  }
    rset.close();
    stmt.close();
    return estEmprunte;
  }

  /**
   * Récupération des statistiques sur les emprunts (nombre d'emprunts et de
   * retours par jour).
   * 
   * @return un <code>HashMap<String, int[]></code>. Chaque enregistrement de la
   * structure de données est identifiée par la date (la clé) exprimée sous la forme
   * d'une chaîne de caractères. La valeur est un tableau de 2 entiers qui représentent :
   * <ul>
   *   <li>0 : le nombre d'emprunts</li>
   *   <li>1 : le nombre de retours</li>
   * </ul>
   * Exemple :
   * <pre>
   * +-------------------------+
   * | "2017-04-01" --> [3, 1] |
   * | "2017-04-02" --> [0, 1] |
   * | "2017-04-07" --> [5, 9] |
   * +-------------------------+
   * </pre>
   *   
   * @throws SQLException
   */
  public static HashMap<String, int[]> statsEmprunts() throws SQLException
  {
    HashMap<String, int[]> stats = new HashMap<String, int[]>();
    //
    // A COMPLETER
    //
    LinkedList<String> datesEmprunt = new LinkedList<String>();
    LinkedList<String> datesRetour = new LinkedList<String>();
    Statement stmt = Connexion.getConnection().createStatement();
    String sql1 = "select dateemprunt from emprunt order by dateemprunt";
    String sql2 = "select dateretour from emprunt where dateretour is not null";
    
    ResultSet rset1 = stmt.executeQuery(sql1);
    while (rset1.next()){
    	datesEmprunt.add(rset1.getString("dateemprunt"));
    }
    rset1.close(); 
    
    ResultSet rset2 = stmt.executeQuery(sql2);
    while (rset2.next()){
    	datesRetour.add(rset2.getString("dateretour"));
    }
    rset2.close();
    
    String date=datesEmprunt.get(0).substring(0,10);
    int nbEmprunts = 1;
    int nbRetours = 0;
    int i = 1;
    while(i<datesEmprunt.size()){
    	while(i < datesEmprunt.size() && ((datesEmprunt.get(i).substring(0,10)).equals(date)) ){
    	nbEmprunts++;
    	i++;
        }
        
    	for(int j=0;j<datesRetour.size();j++){
    		if((datesRetour.get(j).substring(0,10)).equals(date))
    			nbRetours++;
    	}
    	int [] tab = new int[2];
    	tab[0]=nbEmprunts;
    	tab[1]=nbRetours;
    	stats.put(date,tab);
    	if (i<datesEmprunt.size())
    		{date=datesEmprunt.get(i).substring(0,10);
        	nbEmprunts=0;
        	nbRetours=0;}
    }
   
 
    stmt.close();
    return stats;
  }
}
