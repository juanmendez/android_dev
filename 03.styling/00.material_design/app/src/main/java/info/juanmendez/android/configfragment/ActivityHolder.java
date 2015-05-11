package info.juanmendez.android.configfragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import dagger.ObjectGraph;

/**
 * Created by Juan on 5/10/2015.
 */
@EBean( scope = EBean.Scope.Singleton )
public class ActivityHolder {

    @RootContext
    MainActivity activity;

    private ObjectGraph graph;

    public ActivityHolder()
    {
    }

    @AfterViews
    public void afterInject()
    {
        Logging.print("afterInject");
        graph = ObjectGraph.create(new ActivityModule(activity));
        graph.inject(this);
    }

    public ObjectGraph getGraph() {
        return graph;
    }
}
