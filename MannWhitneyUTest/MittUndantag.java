package mannWhitneyUTest;


//Simpelt undantag som kastar när det är för många element i ett array.

public class MittUndantag extends ArrayIndexOutOfBoundsException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MittUndantag(String msg){
		super(msg);
	}
}
