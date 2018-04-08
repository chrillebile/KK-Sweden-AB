import * as React from 'react';
import { PalletActions } from 'app/actions/palletActions';

interface Props {
  resetPalletView: any,
  actions: PalletActions,
}

interface State {
}

export class PalletSearchComponent extends React.Component<Props, State> {
  render() {
    return (
      <div>
        <div>
          <button onClick={this.props.resetPalletView}>Reset View</button>
        </div>
        <div>
          <label>
            Search by id:
            <input type="text" ref="searchByIdTbx"/>
          </label>
          <button onClick={this.handleIdSearch}>Search</button>
        </div>
        <div>
          <label>
            Search by product:
            <input type="text" ref="searchByProductTbx"/>
            <button>Search</button>
          </label>
        </div>
        <div>
          <label>
            Search by timestamp:
            <input type="text"/>
            <button>Search</button>
          </label>
        </div>
        <div>
          <label>
            Search by delivery to customer:
            <input type="text"/>
            <button>Search</button>
          </label>
        </div>
        <div>
          <label>
            Search for blocked pallets:
            <input type="text"/>
            <button>Search</button>
          </label>
        </div>
      </div>
    );
  }

  /**
   * Handle clicking on search by id.
   * @param {React.MouseEvent<HTMLButtonElement>} e
   */
  handleIdSearch = () => {
    this.props.actions.getPalletById(parseInt((this.refs.searchByIdTbx as HTMLInputElement).value));
  };
}

export default PalletSearchComponent;
