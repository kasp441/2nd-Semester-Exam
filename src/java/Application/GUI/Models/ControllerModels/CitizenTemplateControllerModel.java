package Application.GUI.Models.ControllerModels;

import Application.BE.ContactInfo;
import Application.GUI.Models.CategoryEntryModel;
import Application.GUI.Models.CitizenTemplateModel;
import com.sun.source.tree.Tree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.time.LocalDate;

public class CitizenTemplateControllerModel {

    private CitizenTemplateModel selectedCitizenTemplateModel;

    private TreeItem<CategoryEntryModel> preEditHealthCategoryEntryModels;
    private TreeItem<CategoryEntryModel> preEditFunctionCategoryEntryModels;


    public void citizenTemplateSearch() {
    }

    public void removeCitizenTemplateContactInfo() {
    }

    public void addCitizenTemplateContactInfo() {
    }

    public void citizenTemplateChangeJournal() {
    }

    public void onCitizenTemplateEditBaseData() {
    }

    public ObservableList<CitizenTemplateModel> getCitizenTemplates() {
        ObservableList<CitizenTemplateModel> citizenTemplates = FXCollections.observableArrayList();
        citizenTemplates.add(new CitizenTemplateModel("John", "Jørgensen", LocalDate.now(), "Active", "Single", "I am living in your walls", FXCollections.observableArrayList(new ContactInfo("Søn Tlf 12 12 12 12"), new ContactInfo("Datter Tlf 12 12 12 12"))));
        citizenTemplates.add(new CitizenTemplateModel("Mark", "Hansen", LocalDate.now(), "Ikke Aktiv", "Gift", "I am living in your walls", FXCollections.observableArrayList(new ContactInfo("Mor Tlf 12 12 12 12"), new ContactInfo("Far Tlf 12 12 12 12"))));
        return citizenTemplates;
    }

    public void setSelectedCitizenTemplateModel(CitizenTemplateModel selectedCitizenTemplateModel) {
        this.selectedCitizenTemplateModel = selectedCitizenTemplateModel;
    }

    public CitizenTemplateModel getSelectedCitizenTemplateModel() {
        return selectedCitizenTemplateModel;
    }

    public TreeItem<CategoryEntryModel> getAllFuncCategoriesAsTreeItem() {
        TreeItem<CategoryEntryModel> treeItem = new TreeItem<>(new CategoryEntryModel("All Functional Ability Categories"));
        return listToTreeItem(treeItem, selectedCitizenTemplateModel.getAllFuncCategories());
    }

    public TreeItem<CategoryEntryModel> getAllHealthConditionsAsTreeItem() {
        TreeItem<CategoryEntryModel> treeItem = new TreeItem<>(new CategoryEntryModel("All Health Categories"));
        return listToTreeItem(treeItem, selectedCitizenTemplateModel.getAllHealthConditions());
    }

    public TreeItem<CategoryEntryModel> getNewRelevantFuncCategoriesAsTreeItem() {
        TreeItem<CategoryEntryModel> treeItem = new TreeItem<>(new CategoryEntryModel("All Functional Ability Categories"));
        return listToTreeItem(treeItem, selectedCitizenTemplateModel.getRelevantFunctionalAbilities());
        //TODO sort the two lists (relevant and non-relevant) and make a new one
    }

    public TreeItem<CategoryEntryModel> getNewRelevantHealthCategoriesAsTreeItem() {
        TreeItem<CategoryEntryModel> treeItem = new TreeItem<>(new CategoryEntryModel("All Health Categories"));
        return listToTreeItem(treeItem, selectedCitizenTemplateModel.getRelevantHealthConditions());
        //TODO sort the two lists (relevant and non-relevant) and make a new one
    }

    public TreeItem<CategoryEntryModel> getRelevantFuncCategoriesAsTreeItem() {
        TreeItem<CategoryEntryModel> treeItem = new TreeItem<>(new CategoryEntryModel("All Functional Ability Categories"));
        return listToTreeItem(treeItem, selectedCitizenTemplateModel.getRelevantFunctionalAbilities());
    }

    public TreeItem<CategoryEntryModel> getRelevantHealthCategoriesAsTreeItem() {
        TreeItem<CategoryEntryModel> treeItem = new TreeItem<>(new CategoryEntryModel("All Health Categories"));
        return listToTreeItem(treeItem, selectedCitizenTemplateModel.getRelevantHealthConditions());
    }

    private TreeItem<CategoryEntryModel> listToTreeItem(TreeItem<CategoryEntryModel> treeItem, ObservableList<CategoryEntryModel> list) {
        for (CategoryEntryModel categoryEntryModel : list) {
            treeItem.getChildren().add(categoryEntryModel.getAsTreeItem());
        }
        return treeItem;
    }

    public CitizenTemplateModel newCitizenTemplate() {
        //TODO Write to DB
        CitizenTemplateModel CitizenTemplateModel = new CitizenTemplateModel("Ny", "Skabelon", LocalDate.now(), "", "", "", FXCollections.observableArrayList());
        //DataManager.newCitizenTemplate(CitizenTemplateModel);

        return CitizenTemplateModel;
    }

    public void deleteCitizenTemplate() {
        //TODO delete from DB
        //DataManager.deleteCitizenTemplate(selectedCitizenTemplateModel);
    }

    public void copyCitizenTemplate() {
    }

    public TreeItem<CategoryEntryModel> getPreEditHealthCategoryEntryModels() {
        return preEditHealthCategoryEntryModels;
    }

    public void setPreEditHealthCategoryEntryModels(TreeItem<CategoryEntryModel> preEditHealthCategoryEntryModels) {
        this.preEditHealthCategoryEntryModels = preEditHealthCategoryEntryModels;
    }

    public TreeItem<CategoryEntryModel> getPreEditFunctionCategoryEntryModels() {
        return preEditFunctionCategoryEntryModels;
    }

    public void setPreEditFunctionCategoryEntryModels(TreeItem<CategoryEntryModel> preEditFunctionCategoryEntryModels) {
        this.preEditFunctionCategoryEntryModels = preEditFunctionCategoryEntryModels;
    }

    public void saveEditedCitizenTemplate() {
        //TODO save to DB
    }
}
