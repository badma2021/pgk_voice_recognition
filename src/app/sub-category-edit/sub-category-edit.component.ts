
import { Component } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { SubCategoryEditService } from '../_services/sub-category-edit.service';
import { SubCategory } from '../types/subCategory';
import { CreateExpense } from '../types/createExpense';
import { RecordListService } from '../_services/record-list.service';

import { Router } from '@angular/router';

@Component({
  selector: 'app-sub-category-edit',
  templateUrl: './sub-category-edit.component.html',
  styleUrls: ['./sub-category-edit.component.css']
})
export class SubCategoryEditComponent {
filterTypes ;
  subCategories: SubCategory[] = []
expenseTitleIds = [];
  editedId: number | null = null;
  editedName = ''
  newSubCategoryName = ''
  userId: number | null = null;
  isSaving = false;
  isSubmitting = false;
  categoryId : number | null = null;
  expenseId : string='0';
  constructor(private recordListService: RecordListService, private subCategoryEditService: SubCategoryEditService, private tokenStorage: TokenStorageService, private router: Router) {}


  ngOnInit() {
  this.userId = this.tokenStorage.getUser().userId;
        this.userId = this.tokenStorage.getUser().userId;
      this.recordListService.getCategories(this.userId).subscribe(

            data => this.filterTypes = data
          // console.log(data)
          );
  }


  onChangeCategory(categoryId: number) {
    console.log("hi from onChangeCategory");
this.categoryId = categoryId;
       if (categoryId) {
         this.recordListService.getExpenseTitle(categoryId).subscribe(
           data =>
             this.expenseTitleIds = data

         );
       } else {
       console.log("hi from onChangeCategory else");
         this.expenseTitleIds = null;

       }
         this.expenseId='0';
     }
  loadSubCategories(userId: number) {
    this.subCategoryEditService.getAll(this.userId)
      .subscribe(res => this.subCategories = res)
  }

  startEdit(cat: SubCategory) {
    this.editedId = cat.id
    this.editedName = cat.expenseName
  }

saveEdit(cat: SubCategory) {
  // валидация
  if (!this.editedName?.trim()) { return; }

  // получить userId заранее
  this.userId = this.tokenStorage.getUser().userId;

  const updated = { id: cat.id, expenseName: this.editedName };

  this.isSaving = true;
  this.subCategoryEditService.update(updated).subscribe({
    next: () => {
      this.loadSubCategories(this.userId);
      this.cancelEdit();
    },
    error: (err) => {
      // показать ошибку пользователю (toast/snackbar)
      console.error('Update failed', err);
    },
    complete: () => {
      this.isSaving = false;
    }
  });
}

cancelEdit() {
  if (this.isSaving) { return; } // опционально блокируем отмену во время сохранения
  this.editedId = null;
  this.editedName = '';
}


addSubCategory(): void {
  // простая валидация
  const name = (this.newSubCategoryName || '').trim();
  if (!name) {
    console.warn('Название подкатегории пустое');
    return;
  }
  if (this.categoryId == null) {
    console.warn('ID категории не задан');
    return;
  }
  const createExpense = {
    expenseName: name,
    categoryId: this.categoryId
  };
  this.isSubmitting = true;
  this.subCategoryEditService.create(createExpense).subscribe({
    next: (res) => {

      this.newSubCategoryName = '';

      this.loadSubCategories(this.userId);
    },
    error: (err) => {
      console.error('Ошибка при создании подкатегории', err);
    },
    complete: () => {
      this.isSubmitting = false;
    }
  });
}



deleteExpense(expense: any) {
  this.isSaving = true;
  this.subCategoryEditService.deleteExpense(expense.id).subscribe({
    next: () => {
      // удалить из локального массива
      this.expenseTitleIds = this.expenseTitleIds.filter(e => e.id !== expense.id);
      // если редактировали — сбросить форму
      if (this.editedId === expense.id) {
        this.cancelEdit();
      }
      this.isSaving = false;
    },
    error: err => {
      console.error(err);
      // показать уведомление пользователю
      this.isSaving = false;
    }
  });
}

}
