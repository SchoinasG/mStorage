package com.example.johnnie.mstorage;

import android.provider.BaseColumns;

/**
 * Created by Masterace on 5/6/2017.
 */

public class DBContract {

    public static final class StorageEntry implements BaseColumns {
        public static final String TABLE_NAME = "storages";

        public static final String COLUMN_STORAGE_ID = "stor_id";

        public static final String COLUMN_STORAGE_NAME = "stor_name";
    }

    public static final class DepartmentEntry implements BaseColumns {
        public static final String TABLE_NAME = "departments";

        public static final String COLUMN_DEPARTMENT_ID = "dep_id";

        public static final String COLUMN_DEPARTMENT_NAME = "dep_name";

        public static final String COLUMN_BELONGS_TO_STORAGE = "belongs_to_stor";
    }
}
