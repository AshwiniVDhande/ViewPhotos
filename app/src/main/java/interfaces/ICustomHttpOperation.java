package interfaces;

/**
 * Created by edward.abergas on 03/05/2019.
 *
 * @author edward.abergas@effective.digital
 */
public interface ICustomHttpOperation<T> {
	
	void onCallSuccess(T object);
	
	void onCallFail(String cause, Throwable throwable);
}

