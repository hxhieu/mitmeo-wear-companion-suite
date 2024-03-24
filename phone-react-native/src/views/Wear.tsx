import React, {useEffect, useMemo, useState} from 'react';
import {NativeEventEmitter, NativeModules} from 'react-native';

import BatteryInfo from '../components/BatteryInfo';

interface Props {}

const {WearCommunicationModule} = NativeModules;

const Wear: React.FunctionComponent<Props> = ({}) => {
  // TODO: Typed this
  const [batteryInfo, setBatteryInfo] = useState<any>();

  const batteryPct = useMemo<number>(
    () => Number.parseFloat(batteryInfo?.BATTERY_PCT) || 0,
    [batteryInfo],
  );

  useEffect(() => {
    const eventEmitter = new NativeEventEmitter(WearCommunicationModule);
    let eventListener = eventEmitter.addListener('BATTERY_INFO', data => {
      setBatteryInfo(data);
    });

    // Removes the listener once unmounted
    return () => {
      eventListener.remove();
    };
  }, []);

  return (
    <>
      <BatteryInfo pct={batteryPct} />
    </>
  );
};

export default Wear;
