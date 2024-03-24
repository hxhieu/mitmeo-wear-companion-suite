/**
 * @format
 */

import {AppRegistry} from 'react-native';
import {RealmProvider} from '@realm/react';

import App from './App';
import {name as appName} from './app.json';
import models from './src/models';

function AppWrapper() {
  const realmConfig = {
    schema: models,
    // TODO: Local dev only
    deleteRealmIfMigrationNeeded: true,
  };
  return (
    <RealmProvider {...realmConfig}>
      <App />
    </RealmProvider>
  );
}

AppRegistry.registerComponent(appName, () => AppWrapper);
