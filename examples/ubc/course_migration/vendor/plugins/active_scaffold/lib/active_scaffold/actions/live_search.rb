module ActiveScaffold::Actions
  module LiveSearch
    def self.included(base)
      base.before_filter :search_authorized_filter, :only => :show_search
      base.before_filter :do_search
    end

    def show_search
      respond_to_action(:live_search)
    end

    protected

    def live_search_respond_to_html
      if successful?
        render(:partial => "live_search", :layout => true)
      else
        return_to_main
      end
    end
    
    def live_search_respond_to_js
      render(:partial => "live_search")
    end

    def do_search
      @query = params[:search].to_s.strip rescue ''

      unless @query.empty?
        columns = active_scaffold_config.live_search.columns
        text_search = active_scaffold_config.live_search.text_search
        search_conditions = self.class.create_conditions_for_columns(@query.split(' '), columns, text_search)
        self.active_scaffold_conditions = merge_conditions(self.active_scaffold_conditions, search_conditions)
        @filtered = !search_conditions.blank?

        includes_for_search_columns = columns.collect{ |column| column.includes}.flatten.uniq.compact
        self.active_scaffold_includes.concat includes_for_search_columns

        active_scaffold_config.list.user.page = nil
      end
    end

    # The default security delegates to ActiveRecordPermissions.
    # You may override the method to customize.
    def search_authorized?
      authorized_for?(:action => :read)
    end
    private
    def search_authorized_filter
      link = active_scaffold_config.live_search.link || active_scaffold_config.live_search.class.link
      raise ActiveScaffold::ActionNotAllowed unless self.send(link.security_method)
    end
    def live_search_formats
      (default_formats + active_scaffold_config.formats + active_scaffold_config.live_search.formats).uniq
    end
  end
end
