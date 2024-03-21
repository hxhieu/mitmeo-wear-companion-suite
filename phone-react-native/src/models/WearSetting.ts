import Realm, {BSON, ObjectSchema} from 'realm';
import {WearBatterySetting} from './WearBatterySetting';

export class WearSetting extends Realm.Object<WearSetting> {
  _id!: BSON.ObjectId;
  device_id: string = '';
  device_name?: string;
  battery_setting?: WearBatterySetting;

  static schema: ObjectSchema = {
    name: 'WearSetting',
    properties: {
      _id: 'objectId',
      device_id: {type: 'string', indexed: true},
      device_name: 'string?',
      battery_setting: 'WearBatterySetting?',
    },
  };
}
