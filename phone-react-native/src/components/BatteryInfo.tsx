import React from 'react';
import {LinearProgress} from '@rneui/themed';

interface Props {
  pct?: number;
}

const BatteryInfo: React.FunctionComponent<Props> = ({pct}) => {
  return (
    <LinearProgress
      style={{marginVertical: 10, height: 20}}
      value={pct}
      variant="determinate"
    />
  );
};

export default BatteryInfo;
