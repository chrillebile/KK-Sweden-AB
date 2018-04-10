import * as React from 'react';
import { Route } from 'react-router';
import { BrowserRouter } from 'react-router-dom'

import { hot } from 'react-hot-loader';
import PalletSearch from 'Containers/PalletSearch/PalletSearch';
import PalletCreate from 'Containers/PalletCreate';

export const App = hot(module)(() => (
  <BrowserRouter>
    <div>
      <Route exact path="/" component={PalletSearch} />
      <Route path="/createPallet" component={PalletCreate} />
    </div>
  </BrowserRouter>
));
