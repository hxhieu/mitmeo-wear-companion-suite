import React, {useEffect} from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  useColorScheme,
  View,
  NativeModules,
  AppState,
  AppStateStatus,
} from 'react-native';

import {useQuery, useRealm} from '@realm/react';

import {Colors} from 'react-native/Libraries/NewAppScreen';

import {AppSetting} from './src/models/AppSetting';
import {BSON} from 'realm';
import Wear from './src/views/Wear';

const {WearCommunicationModule} = NativeModules;

function appStateHandler(state: AppStateStatus) {
  console.log(state);
  if (state === 'active') {
    WearCommunicationModule.requestBatteryInfo();
  }
}

function App(): React.JSX.Element {
  const realm = useRealm();
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  useEffect(() => {
    WearCommunicationModule.requestBatteryInfo();
    AppState.addEventListener('change', appStateHandler);
  }, []);

  const test = useQuery(AppSetting);
  console.log(test.at(0));
  if (!test?.length) {
    realm.write(() => {
      realm.create('AppSetting', {
        _id: new BSON.ObjectId(),
        features: ['test'],
      });
    });
  }

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={backgroundStyle}>
        <View
          style={{
            backgroundColor: isDarkMode ? Colors.black : Colors.white,
          }}>
          <Wear />
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

// const styles = StyleSheet.create({
//   sectionContainer: {
//     marginTop: 32,
//     paddingHorizontal: 24,
//   },
//   sectionTitle: {
//     fontSize: 24,
//     fontWeight: '600',
//   },
//   sectionDescription: {
//     marginTop: 8,
//     fontSize: 18,
//     fontWeight: '400',
//   },
//   highlight: {
//     fontWeight: '700',
//   },
// });

export default App;
