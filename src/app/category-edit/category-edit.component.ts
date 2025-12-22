import { Component } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { CategoryEditService } from '../_services/category-edit.service';
import { Category } from '../types/category';

import { Router } from '@angular/router';

@Component({
  selector: 'app-category-edit',
  templateUrl: './category-edit.component.html',
  styleUrls: ['./category-edit.component.css']
})
export class CategoryEditComponent {
  categories: Category[] = []
  editedId: number|null = null
  editedName = ''
  newCategoryName = ''
  userId = null
  isSaving = false;
  constructor(private categoryEditService: CategoryEditService, private tokenStorage: TokenStorageService, private router: Router) {}


  ngOnInit() {
  this.userId = this.tokenStorage.getUser().userId;
    this.loadCategories(this.userId)
  }

  loadCategories(userId: number) {
    this.categoryEditService.getAll(this.userId)
      .subscribe(res => this.categories = res)
  }

  startEdit(cat: Category) {
    this.editedId = cat.id
    this.editedName = cat.categoryName
  }

saveEdit(cat: Category) {
  // валидация
  if (!this.editedName?.trim()) { return; }

  // получить userId заранее
  this.userId = this.tokenStorage.getUser().userId;

  const updated = { id: cat.id, categoryName: this.editedName };

  this.isSaving = true;
  this.categoryEditService.update(updated).subscribe({
    next: () => {
      this.loadCategories(this.userId);
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

deleteCategory(cat: any) {
  this.isSaving = true;
  this.categoryEditService.deleteCategory(cat.id).subscribe({
    next: () => {
      // удалить из локального массива
     // this.expenseTitleIds = this.expenseTitleIds.filter(e => e.id !== expense.id);
      // если редактировали — сбросить форму
      if (this.editedId === cat.id) {
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

  addCategory() {
    const category = {
      id: 0,
      categoryName: this.newCategoryName,
      userId: this.userId
    }
  this.userId = this.tokenStorage.getUser().userId;
    this.categoryEditService.create(category)
      .subscribe(() => this.loadCategories(this.userId))

    this.newCategoryName = ''
  }
}
