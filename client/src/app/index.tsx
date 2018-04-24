import * as React from 'react';
import { Route } from 'react-router';
import { BrowserRouter } from 'react-router-dom'

import { hot } from 'react-hot-loader';
import PalletSearch from 'Containers/PalletSearch/PalletSearch';
import PalletCreate from 'Containers/PalletCreate';
import { PalletBlock } from 'Containers/PalletBlock/PalletBlock';
import '../assets/style.scss';

export const App = hot(module)(() => (
  <BrowserRouter>
    <div className="container">
      <Route exact path="/" component={PalletSearch} />
      <Route path="/createPallet" component={PalletCreate} />
      <Route path="/blockPallet" component={PalletBlock} />
    </div>
  </BrowserRouter>
));
