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
    customerId: number | string,
    fromDate: string,
    toDate: string
  }
}

export class PalletSearchComponent extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);

    this.state = {
      formState: {
        blocked: false,
        customerId: '',
        fromDate: '',
        toDate: ''
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
            <input
              type="date"
              onChange={this.handleFromDateChange}
              min="1970-01-01"
              max={this.state.formState.toDate}
              value={this.state.formState.fromDate}
              required
            />
            <input
              type="date"
              onChange={this.handleToDateChange}
              min={this.state.formState.fromDate}
              value={this.state.formState.toDate}
              required
            />
            <button onClick={this.handleTimeSpanSearch}>Search</button>
          </label>
        </div>
        <div>
          <label>
            Search by delivery to customer:
            <input
              type="text"
              onChange={this.handleCustomerIdChange}
              placeholder="Enter id of customer"
              value={this.state.formState.customerId}
            />
            <button onClick={this.handleCustomerIdSearch}>Search</button>
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

  handleCustomerIdChange = (event: ChangeEvent<HTMLInputElement>): void => {
    this.setState({
      formState: Object.assign({}, this.state.formState, { customerId: event.target.value })
    });
  };

  handleFromDateChange = (event: ChangeEvent<HTMLInputElement>): void => {
    this.setState({
      formState: Object.assign({}, this.state.formState, { fromDate: event.target.value })
    });
  };

  handleToDateChange = (event: ChangeEvent<HTMLInputElement>): void => {
    this.setState({
      formState: Object.assign({}, this.state.formState, { toDate: event.target.value })
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

  /**
   * Handle clicking on search by customer id.
   * @param {React.MouseEvent<HTMLButtonElement>} e
   */
  handleCustomerIdSearch = () => {
    this.props.actions.getPalletsByCustomerId(parseInt(this.state.formState.customerId as string));
  };

  /**
   * Handle clicking on search by customer id.
   * @param {React.MouseEvent<HTMLButtonElement>} e
   */
  handleTimeSpanSearch = () => {
    this.props.actions.getPalletsByTimespan(this.state.formState.fromDate, this.state.formState.toDate);
  };
}

export default PalletSearchComponent;
