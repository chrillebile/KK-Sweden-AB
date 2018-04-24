import * as React from 'react';
import { ChangeEvent } from 'react';
import { PalletActions } from 'app/actions/palletActions';
import 'react-toastify/dist/ReactToastify.css';


interface Props {
  resetPalletView: any,
  actions: PalletActions,
}

interface State {
  formState: {
    blocked: boolean,
    customerId: number | string,
    fromDate: string,
    toDate: string,
    productId: number | string
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
        toDate: '',
        productId: ''
      }
    };
  }

  render() {
    return (
      <div className="palletSearches">
        <div>
          <button onClick={this.props.resetPalletView}>Reset View</button>
        </div>
        <div className="palletSearchItem">
          <label>
            Search by id:
            <br/>
            <input
              type="text"
              ref="searchByIdTbx"
              placeholder="Enter id of pallet"
            />
          </label>
          <button onClick={this.handleIdSearch}>Search</button>
        </div>
        <div className="palletSearchItem">
          <label>
            Search by product:
            <br/>
            <input
              type="text"
              onChange={this.handleProductIdChange}
              value={this.state.formState.productId}
              placeholder="Enter id of product"
            />
            <button onClick={this.handleProductIdSearch}>Search</button>
          </label>
        </div>
        <div className="palletSearchItem">
          <label>
            Search by timestamp:
            <br/>
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
        <div className="palletSearchItem">
          <label>
            Search by delivery to customer:
            <br/>
            <input
              type="text"
              onChange={this.handleCustomerIdChange}
              placeholder="Enter id of customer"
              value={this.state.formState.customerId}
            />
            <button onClick={this.handleCustomerIdSearch}>Search</button>
          </label>
        </div>
        <div className="palletSearchItem">
          <label>
            Search for blocked pallets:
            <br/>
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

  handleProductIdChange = (event: ChangeEvent<HTMLInputElement>): void => {
    this.setState({
      formState: Object.assign({}, this.state.formState, { productId: event.target.value })
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

  /**
   * Handle clicking on search by product id.
   * @param {React.MouseEvent<HTMLButtonElement>} e
   */
  handleProductIdSearch = () => {
    this.props.actions.getPalletsByProductId(parseInt(this.state.formState.productId as string));
  };
}

export default PalletSearchComponent;
