import * as React from 'react';
import { bindActionCreators, Dispatch } from 'redux';
import { connect } from 'react-redux';
import { RootState } from 'app/reducers';
import { omit } from 'app/utils';
import Header from 'Containers/Header';
import { RecipeActions } from 'app/actions';
import { ChangeEvent } from 'react';
import { PalletActions } from 'app/actions';

interface Props {
  recipeActions: RecipeActions,
  palletActions: PalletActions,
  recipes: RootState.RecipeState
}

interface State {
  formState: {
    fromDate: string,
    toDate: string,
    recipeId: number | string
  }
}

@connect((state: RootState) => {
  return {
    recipes: state.recipes
  };
}, (dispatch: Dispatch<RootState>) => ({
  recipeActions: bindActionCreators(omit(RecipeActions, 'Type'), dispatch),
  palletActions: bindActionCreators(omit(PalletActions, 'Type'), dispatch)
}))

export class PalletBlock extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);

    this.state = {
      formState: {
        fromDate: '',
        toDate: '',
        recipeId: ''
      }
    };
  }


  componentWillMount() {
    // Update the list of pallets in state
    this.props.recipeActions.getRecipes();
  }

  render() {
    return (
      <div>
        <Header/>
        <div>

          <label>
            Block by recipe:
            <select onChange={this.handleRecipeChange}>
              <option disabled selected>--Choose an option--</option>
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
            Block by date range:
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

          </label>
          <button onClick={this.handlePalletBlock}>Block pallets</button>
        </div>
      </div>
    );
  }

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

  handleRecipeChange = (event: ChangeEvent<HTMLSelectElement>): void => {
    this.setState({
      formState: Object.assign({}, this.state.formState, { recipeId: parseInt(event.target.options[event.target.selectedIndex].value) })
    });
  };

  handlePalletBlock = () => {
    this.props.palletActions.changeBlockedStatus(this.state.formState.recipeId as number, this.state.formState.fromDate, this.state.formState.toDate);
  };

}

export default PalletBlock;
