package mannWhitneyUTest;


//Simpelt undantag som kastar n�r det �r f�r m�nga element i ett array.

public class MittUndantag extends ArrayIndexOutOfBoundsException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MittUndantag(String msg){
		super(msg);
	}
}
