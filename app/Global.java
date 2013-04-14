import play.GlobalSettings;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 14.01.13
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */
public class Global extends GlobalSettings {
    public play.mvc.Result onBadRequest(play.mvc.Http.RequestHeader requestHeader, java.lang.String s)
    {
        return super.onBadRequest(requestHeader, s);
    }


}
