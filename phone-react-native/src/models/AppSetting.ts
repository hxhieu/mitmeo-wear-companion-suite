import Realm, {BSON, ObjectSchema} from 'realm';
import {WearSetting} from './WearSetting';

export class AppSetting extends Realm.Object<AppSetting> {
  _id!: BSON.ObjectId;
  wears: Realm.List<WearSetting> = new Realm.List<WearSetting>();
  features: string[] = [];

  static schema: ObjectSchema = {
    name: 'AppSetting',
    properties: {
      _id: 'objectId',
      wears: 'WearSetting[]',
      features: 'string[]',
    },
  };
}
