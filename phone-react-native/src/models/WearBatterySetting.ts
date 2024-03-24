import Realm, {BSON, ObjectSchema} from 'realm';

export class WearBatterySetting extends Realm.Object<WearBatterySetting> {
  _id!: BSON.ObjectId;
  nagging_interval_mins: number = 60;

  static schema: ObjectSchema = {
    name: 'WearBatterySetting',
    properties: {
      _id: 'objectId',
      nagging_interval_mins: 'int',
      wear: {
        type: 'linkingObjects',
        objectType: 'WearSetting',
        property: 'battery_setting',
      },
    },
  };
}
