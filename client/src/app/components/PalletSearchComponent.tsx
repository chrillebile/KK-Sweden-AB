import * as React from 'react';
import { PalletActions } from 'app/actions/palletActions';
import { ChangeEvent } from 'react';

interface Props {
  resetPalletView: any,
  actions: PalletActions,
}

interface State {
  formState: {
    blocked: boolean,
  }
}

export class PalletSearchComponent extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);

    this.state = {
      formState: {
        blocked: false
      }
    };
  }

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
            <label>
              <input
                type="checkbox"
                onChange={this.handleBlockedChange}
                checked={this.state.formState.blocked}
              />
              IsBlocked
            </label>
            <button onClick={this.handleBlockedSearch}>Search</button>
          </label>
        </div>
      </div>
    );
  }

  handleBlockedChange = (event: ChangeEvent<HTMLInputElement>): void => {
    this.setState({
      formState: Object.assign({}, this.state.formState, { blocked: event.target.checked })
    });
  };

  /**
   * Handle clicking on search by id.
   * @param {React.MouseEvent<HTMLButtonElement>} e
   */
  handleIdSearch = () => {
    this.props.actions.getPalletById(parseInt((this.refs.searchByIdTbx as HTMLInputElement).value));
  };

  /**
   * Handle clicking on search by blocked status.
   * @param {React.MouseEvent<HTMLButtonElement>} e
   */
  handleBlockedSearch = () => {
    this.props.actions.getPalletByBlockedStatus(this.state.formState.blocked);
  };
}

export default PalletSearchComponent;
