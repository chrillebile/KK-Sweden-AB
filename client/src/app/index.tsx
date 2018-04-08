import * as React from 'react';
import { Route, Switch } from 'react-router';

import { hot } from 'react-hot-loader';
import PalletSearch from 'Containers/PalletSearch/PalletSearch';

export const App = hot(module)(() => (
  <Switch>
    <Route path="/" component={PalletSearch} />
  </Switch>
));
