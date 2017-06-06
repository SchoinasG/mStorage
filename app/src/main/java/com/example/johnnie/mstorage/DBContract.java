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

    public static final class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "items";

        public static final String COLUMN_ITEM_ID = "item_id";

        public static final String COLUMN_ITEM_NAME = "item_name";

        public static final String COLUMN_ITEM_DESCRIPTION = "item_desc";

        public static final String COLUMN_ITEM_SKU = "item_sku";

        public static final String COLUMN_ITEM_MEASUREMENT = "item_measurement_unit";

        public static final String COLUMN_ITEM_CATEGORY = "item_category";

        public static final String COLUMN_ITEM_POSITION = "item_position";

        public static final String COLUMN_ITEM_BARCODE = "item_barcode";

        public static final String COLUMN_ITEM_QUANTITY = "item_quantity";

        public static final String COLUMN_ITEMS_DEPARTMENT_ID = "item_department_id";
    }
}
