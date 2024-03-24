import React, {useEffect} from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  useColorScheme,
  View,
  Button,
  NativeModules,
  NativeEventEmitter,
} from 'react-native';
import {useQuery, useRealm} from '@realm/react';

import {Colors} from 'react-native/Libraries/NewAppScreen';

import {AppSetting} from './src/models/AppSetting';
import {BSON} from 'realm';

const {WearCommunicationModule} = NativeModules;

function App(): React.JSX.Element {
  const realm = useRealm();
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  useEffect(() => {
    const eventEmitter = new NativeEventEmitter(WearCommunicationModule);
    let eventListener = eventEmitter.addListener('BATTERY_INFO', event => {
      console.log(event); // "someValue"
    });

    // Removes the listener once unmounted
    return () => {
      eventListener.remove();
    };
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
          <Button
            title="Click me!"
            onPress={() => {
              WearCommunicationModule.requestBatteryInfo();
            }}
          />
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
