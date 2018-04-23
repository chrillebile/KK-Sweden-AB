import * as React from 'react';
import { Link } from 'react-router-dom';

/**
 * Header container that contains link to different parts of the app.
 */
export class Header extends React.Component {
  render() {
    return (
      <header>
        <Link to="/">Home</Link>
        <Link to="/createPallet">Create Pallet</Link>
        <Link to="/blockPallet">Block Pallet</Link>
      </header>
    );
  }
}

export default Header;
