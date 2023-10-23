import 'react-native-worklets-core/src';
import { AppRegistry } from 'react-native';
import App from './src/App';
import { name as appName } from './app.json';

// @ts-ignore
AppRegistry.registerComponent(appName, () => App);
