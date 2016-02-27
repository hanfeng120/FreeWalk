package club.hanfeng.freewalk.mainpage;

/**
 * Created by HanFeng on 2016/1/24.
 */
public class MainPageConstants {

    public static final int INDEX_HOME_PAGE = 0;
    public static final int INDEX_SERVER_PAGE = 1;
    public static final int INDEX_FIND_PAGE = 2;
    public static final int INDEX_USER_PAGE = 3;

    public static final int REQUEST_USER_PAGE = INDEX_USER_PAGE;

    public static final String EXTRA_TYPE_FROM = "extra_type_from";

    public static final int EXTRA_TYPE_FROM_SCENE_LIST = 1;

    public static final String EXTRA_TYPE_SCENE_LIST_SCENE_NAME = "extra_type_scene_list_scene_name";
    public static final String EXTRA_TYPE_SCENE_LIST_SCENE_IMAGE_MAP = "extra_type_scene_list_scene_image_map";
    public static final String EXTRA_TYPE_SCENE_LIST_CITY_CODE = "extra_type_scene_list_city_code";
    public static final String EXTRA_TYPE_SCENE_LIST_CITY_NAME = "extra_type_scene_list_city_name";

    public static final int TASK_ID_USERPAGE = 0;

    public static final int TASK_ID_FINDPAGE = TASK_ID_USERPAGE + 1;

    public static final int TASK_ID_SERVERPAGE = TASK_ID_FINDPAGE + 1;

    public static final int TASK_ID_HOMEPAGE = TASK_ID_SERVERPAGE + 1;

    public static final int TASK_ID_SCENE_CHANGED = TASK_ID_HOMEPAGE + 1;

}
