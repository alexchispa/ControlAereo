describe('Nave Page', () => {
  beforeEach(() => {
    cy.visit('http://localhost:5173/nave')
  })

  it('Navega y carga la lista', () => {
    cy.get('.nave-container').should('exist')
    cy.get('.table-container tbody tr').should('have.length.greaterThan', 0)
  })

  it('Entra al formulario', () => {
    cy.get('.create-button').click()
    cy.url().should('include', '/naveForm')
    cy.get('h1').should('contain', 'Nueva Nave')
  })

  it('Crea un nuevo formulario exitosamente', () => {
    cy.get('.create-button').click()
    cy.url().should('include', '/naveForm')
    cy.get('#nombre').type('Nueva Nave')
    cy.get('#piloto').type('Piloto Desconocido')
    cy.get('#colores').type('Rojo y Blanco')
    cy.get('#base').type('Base Desconocida')
    cy.get('#modificaciones').type('Modificaciones de la nueva nave')
    cy.get('#anyoFabricacion').type('2023')
    cy.get('#peso').type('1000')
    cy.get('#longitud').type('30')
    cy.get('#bodega').type('50')
    cy.get('#carga').type('200')
    cy.get('#capacidadPersonas').type('10')
    cy.get('#tipo').find('option:not([disabled])').then(options => {
      const randomIndex = Math.floor(Math.random() * options.length)
      const randomOption = options[randomIndex].text
      cy.get('#tipo').select(randomOption)
    })
    
    cy.get('.submit-button').click()
    cy.url().should('include', '/nave')
    cy.get('table.custom-table tbody tr').should('contain', 'Nueva Nave')
  })

  it('Navega a la página de actualización', () => {
    cy.get('table.custom-table tbody tr').first().find('.action-button').contains('Editar').click({ force: true })
    cy.url().should('include', '/naveForm')
  })

  it('Actualiza una nave', () => {
    cy.get('table.custom-table tbody tr').first().find('.action-button').contains('Editar').click({ force: true })
    cy.url().should('include', '/naveForm')
    cy.get('#nombre').clear().type('Nave Actualizada')
    cy.get('#tipo').find('option:not([disabled])').then(options => {
      const randomIndex = Math.floor(Math.random() * options.length)
      const randomOption = options[randomIndex].text
      cy.get('#tipo').select(randomOption)
    })
    cy.get('.submit-button').click()
    cy.get('.confirmation-dialog .submit-button', { timeout: 10000 }).should('be.visible').click()
    cy.url().should('include', '/nave')
    cy.get('table.custom-table tbody tr').first().should('contain', 'Nave Actualizada')
  })

  it('Borra una nave', () => {
    cy.get('table.custom-table tbody tr').first().find('.delete-button').click({ force: true })
    cy.get('.delete-dialog .confirm-button', { timeout: 10000 }).should('be.visible').click()
    cy.url().should('include', '/nave')
    cy.get('.modal-aviso', { timeout: 10000 }).should('contain', 'Nave borrada exitosamente')
  })
})