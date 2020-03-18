package observer;

public interface Observable {

	public void addObservateur(Observateur obs);

	public void updateObservateur(int enCours , int fin,String step);

	public void deleteObservateur();

}
