import * as React from 'react';
import { ChangeEvent, SyntheticEvent } from 'react';
import { bindActionCreators, Dispatch } from 'redux';
import { connect } from 'react-redux';
import { RootState } from 'app/reducers';
import { omit } from 'app/utils';
import Header from 'Containers/Header';
import { RecipeActions } from 'app/actions/recipeActions';
import PalletModel from 'Models/PalletModel';
import { PalletActions } from 'app/actions';

interface Props {
  recipeActions: RecipeActions,
  palletActions: PalletActions
  recipes: RootState.RecipeState
}

interface State {
  formState: {
    amount: string | number,
    productionDate: string,
    location: string,
    blocked: boolean,
    recipeId: number,
    orderId: number
  }
}

@connect((state: RootState) => {
  return {
    recipes: state.recipes
  };
}, (dispatch: Dispatch<RootState>) => ({
  recipeActions: bindActionCreators(omit(RecipeActions, 'Type'), dispatch),
  palletActions: bindActionCreators(omit(PalletActions, 'Type'), dispatch),
}))

export class PalletCreate extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);

    this.state = {
      formState: {
        amount: '',
        productionDate: '',
        location: '',
        blocked: false,
        recipeId: 0,
        orderId: 0
      }
    };
  }

  componentWillMount() {
    this.props.recipeActions.getRecipes();
  }

  render() {
    return (
      <div>
        <Header/>
        <form onSubmit={this.handleSubmit}>
          <div>
            <label>
              Amount:
              <input
                type="number"
                onChange={this.handleAmountChange}
                value={this.state.formState.amount}
                placeholder="Enter amount"
                required/>
            </label>
          </div>
          <div>
            <label>
              Production Date:
              <input
                type="date"
                onChange={this.handleProductionDateChange}
                min="1970-01-01"
                value={this.state.formState.productionDate}
                required
              />
            </label>
          </div>
          <div>
            <label>
              Location:
              <input
                type="text"
                onChange={this.handleLocationChange}
                value={this.state.formState.location}
              />
            </label>
          </div>
          <div>
            <label>
              Blocked:
              <input
                type="checkbox"
                onChange={this.handleBlockedChange}
                checked={this.state.formState.blocked}
              />
            </label>
          </div>
          <div>
            <label>
              Recipe:
              <select onChange={this.handleRecipeChange}>
                <option disabled selected>--Choose a recipe--</option>
                {this.props.recipes.recipeItems.map(recipe => {
                  return (
                    <option value={recipe.id} key={recipe.id}>{recipe.name}</option>
                  );
                })}
              </select>
            </label>
          </div>
          <div>
            <label>
              Order:
              <select onChange={this.handleOrderChange}>
                <option disabled selected>--Choose a recipe--</option>
                <option value="1">Order 1</option>
              </select>
            </label>
          </div>
          <div>
            <button>Create pallet</button>
          </div>
        </form>

      </div>
    );
  }

  handleAmountChange = (event: ChangeEvent<HTMLInputElement>): void => {
    this.setState({
      formState: Object.assign({}, this.state.formState, { amount: parseInt(event.target.value) })
    });
  };

  handleProductionDateChange = (event: ChangeEvent<HTMLInputElement>): void => {
    this.setState({
      formState: Object.assign({}, this.state.formState, { productionDate: event.target.value })
    });
  };

  handleLocationChange = (event: ChangeEvent<HTMLInputElement>): void => {
    this.setState({
      formState: Object.assign({}, this.state.formState, { location: event.target.value })
    });
  };

  handleBlockedChange = (event: ChangeEvent<HTMLInputElement>): void => {
    this.setState({
      formState: Object.assign({}, this.state.formState, { blocked: event.target.checked })
    });
  };

  handleRecipeChange = (event: ChangeEvent<HTMLSelectElement>): void => {
    this.setState({
      formState: Object.assign({}, this.state.formState, { recipeId: parseInt(event.target.options[event.target.selectedIndex].value) })
    });
  };

  handleOrderChange = (event: ChangeEvent<HTMLSelectElement>): void => {
    this.setState({
      formState: Object.assign({}, this.state.formState, { orderId: parseInt(event.target.options[event.target.selectedIndex].value) })
    });
  };

  handleSubmit = (event: SyntheticEvent<any>): void => {
    event.preventDefault();
    let formState = this.state.formState;
    let pallet: PalletModel = {
      id: -1,
      productionDate: formState.productionDate,
      blocked: formState.blocked,
      location: formState.location,
      deliveryTime: null,
      amount: formState.amount as number
    };

    this.props.palletActions.createPallet(pallet, formState.recipeId, formState.orderId);
  };
}

export default PalletCreate;
